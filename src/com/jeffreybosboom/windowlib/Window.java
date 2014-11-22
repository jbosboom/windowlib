/*
 * Copyright 2014 Jeffrey Bosboom.
 * This file is part of stratabot.
 *
 * stratabot is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * stratabot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with stratabot.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.jeffreybosboom.windowlib;

import com.jeffreybosboom.windowlib.Bindings.POINT;
import com.jeffreybosboom.windowlib.Bindings.RECT;
import java.awt.Rectangle;
import org.bridj.Pointer;

/**
 * Represents a window.
 *
 * Windows of other threads may be modified or destroyed at any time, and window
 * handles may be reused, so the return values of any status-querying methods
 * are immediately stale and window operations may affect different windows
 * throughout the lifetime of a Window object.
 * @author Jeffrey Bosboom <jbosboom@csail.mit.edu>
 * @since 11/21/2014
 */
public final class Window {
	/**
	 * The window handle. Pointer-sized, but not meaningfully dereferenceable.
	 * Does not need to be closed (it's a user handle, not a kernel handle).
	 */
	private final Pointer<Void> hwnd;
	private Window(Pointer<Void> hwnd) {
		this.hwnd = hwnd;
	}

	public static Window findWindow(String windowClass, String windowTitle) {
		Pointer<Character> klass = null, title = null;
		try {
			klass = allocateWideString(windowClass);
			title = allocateWideString(windowTitle);
			Pointer<Void> hwnd = Bindings.FindWindow(klass, title);
			return hwnd != null ? new Window(hwnd) : null;
		} finally {
			Pointer.release(klass, title);
		}
	}

	public static Window findWindowByTitle(String windowTitle) {
		return findWindow(null, windowTitle);
	}

	public boolean isMinimized() {
		return Bindings.IsIconic(hwnd) != 0;
	}

	public boolean isMaximized() {
		return Bindings.IsZoomed(hwnd) != 0;
	}

	/**
	 * Returns the bounds of this window's
	 * <a href="http://msdn.microsoft.com/en-us/library/windows/desktop/ms632597%28v=vs.85%29.aspx#application">client area</a>
	 * in screen coordinates (suitable for use with {@link java.awt.Robot}).
	 * @return this window's client area in screen coordinates
	 */
	public Rectangle getClientAreaScreenCoordinates() {
		RECT rect = new RECT();
		Bindings.GetClientRect(hwnd, Pointer.pointerTo(rect));
		int width = rect.right() - rect.left();
		int height = rect.bottom() - rect.top();

		POINT point = new POINT().x(rect.left()).y(rect.top());
		Bindings.ClientToScreen(hwnd, Pointer.pointerTo(point));
		return new Rectangle(point.x(), point.y(), width, height);
	}

	/**
	 * Attempts to set this window as the foreground window.  Windows only
	 * permits this <a href="http://msdn.microsoft.com/en-us/library/windows/desktop/ms633539%28v=vs.85%29.aspx">under certain conditions</a>.
	 * @return true iff this window was set as the foreground window
	 * @see #bringToTop()
	 */
	public boolean setInForeground() {
		return Bindings.SetForegroundWindow(hwnd) != 0;
	}

	/**
	 * Attempts to bring this window to the top of the z-order.  Windows only
	 * permits this <a href="http://msdn.microsoft.com/en-us/library/windows/desktop/ms633539%28v=vs.85%29.aspx">under certain conditions</a>.
	 * This function seems to only work if the window is in the same process as
	 * the foreground window.
	 * @return true iff the window was brought to the top
	 * @see #setInForeground()
	 */
	public boolean bringToTop() {
		return Bindings.BringWindowToTop(hwnd) != 0;
	}

	private static Pointer<Character> allocateWideString(String string) {
		if (string == null) return null;
		Pointer<Character> p = Pointer.pointerToWideCString(string);
		if (p == null) throw new OutOfMemoryError(string);
		return p;
	}
}
