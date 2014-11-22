/*
 * Copyright 2014 Jeffrey Bosboom.
 * This file is part of windowlib.
 *
 * windowlib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * windowlib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with windowlib.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.jeffreybosboom.windowlib;

import org.bridj.BridJ;
import org.bridj.CRuntime;
import org.bridj.LastError;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Convention;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * BridJ interop.  Mostly JNAerator-generated boilerplate.
 * @author Jeffrey Bosboom <jbosboom@csail.mit.edu>
 * @since 11/3/2014
 */
@Library("User32")
@org.bridj.ann.Runtime(CRuntime.class)
final class Bindings {
	static {
		BridJ.register();
	}
	private Bindings() {}

	static final class POINT extends StructObject {
		static {
			BridJ.register();
		}
		@Field(0)
		public int x() {
			return this.io.getIntField(this, 0);
		}
		@Field(0)
		public POINT x(int x) {
			this.io.setIntField(this, 0, x);
			return this;
		}
		@Field(1)
		public int y() {
			return this.io.getIntField(this, 1);
		}
		@Field(1)
		public POINT y(int y) {
			this.io.setIntField(this, 1, y);
			return this;
		}
		public POINT() {
			super();
		}
		@SuppressWarnings("unchecked")
		public POINT(Pointer pointer) {
			super(pointer);
		}
	}


	public static final class RECT extends StructObject {
		static {
			BridJ.register();
		}
		@Field(0)
		public int left() {
			return this.io.getIntField(this, 0);
		}
		@Field(0)
		public RECT left(int left) {
			this.io.setIntField(this, 0, left);
			return this;
		}
		@Field(1)
		public int top() {
			return this.io.getIntField(this, 1);
		}
		@Field(1)
		public RECT top(int top) {
			this.io.setIntField(this, 1, top);
			return this;
		}
		@Field(2)
		public int right() {
			return this.io.getIntField(this, 2);
		}
		@Field(2)
		public RECT right(int right) {
			this.io.setIntField(this, 2, right);
			return this;
		}
		@Field(3)
		public int bottom() {
			return this.io.getIntField(this, 3);
		}
		@Field(3)
		public RECT bottom(int bottom) {
			this.io.setIntField(this, 3, bottom);
			return this;
		}
		public RECT() {
			super();
		}
		@SuppressWarnings("unchecked")
		public RECT(Pointer pointer) {
			super(pointer);
		}
	}


	@Convention(Convention.Style.StdCall)
	static native Pointer<Void> FindWindow(Pointer<Character> className, Pointer<Character> windowName) throws LastError;

	@Convention(Convention.Style.StdCall)
	static native int GetClientRect(Pointer<Void> hWnd, Pointer<RECT> rect) throws LastError;

	@Convention(Convention.Style.StdCall)
	static native int ClientToScreen(Pointer<Void> hWnd, Pointer<POINT> rect) throws LastError;

	@Convention(Convention.Style.StdCall)
	static native int SetForegroundWindow(Pointer<Void> hWnd);

	@Convention(Convention.Style.StdCall)
	static native int BringWindowToTop(Pointer<Void> hWnd) throws LastError;

	@Convention(Convention.Style.StdCall)
	static native int IsIconic(Pointer<Void> hWnd);

	@Convention(Convention.Style.StdCall)
	static native int IsZoomed(Pointer<Void> hWnd);
}
