package com.oroarmor.eapplet;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;
import java.util.Random;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

public class EApplet {

	public static class MouseButtons {
		public static final int RIGHT = GLFW.GLFW_MOUSE_BUTTON_RIGHT;
		public static final int LEFT = GLFW.GLFW_MOUSE_BUTTON_LEFT;
	}

	public static class Key {
		public static final int A = GLFW.GLFW_KEY_A;
		public static final int B = GLFW.GLFW_KEY_B;
		public static final int C = GLFW.GLFW_KEY_C;
		public static final int D = GLFW.GLFW_KEY_D;
		public static final int DOWN = GLFW.GLFW_KEY_DOWN;
		public static final int E = GLFW.GLFW_KEY_E;
		public static final int F = GLFW.GLFW_KEY_F;
		public static final int G = GLFW.GLFW_KEY_G;
		public static final int H = GLFW.GLFW_KEY_H;
		public static final int I = GLFW.GLFW_KEY_I;
		public static final int J = GLFW.GLFW_KEY_J;
		public static final int K = GLFW.GLFW_KEY_K;
		public static final int L = GLFW.GLFW_KEY_L;
		public static final int LEFT = GLFW.GLFW_KEY_LEFT;
		public static final int M = GLFW.GLFW_KEY_M;
		public static final int N = GLFW.GLFW_KEY_N;
		public static final int O = GLFW.GLFW_KEY_O;
		public static final int P = GLFW.GLFW_KEY_P;
		public static final int Q = GLFW.GLFW_KEY_Q;
		public static final int R = GLFW.GLFW_KEY_R;
		public static final int RIGHT = GLFW.GLFW_KEY_RIGHT;
		public static final int S = GLFW.GLFW_KEY_S;
		public static final int SPACE = GLFW.GLFW_KEY_SPACE;
		public static final int T = GLFW.GLFW_KEY_T;
		public static final int U = GLFW.GLFW_KEY_U;
		public static final int UP = GLFW.GLFW_KEY_UP;
		public static final int V = GLFW.GLFW_KEY_V;
		public static final int W = GLFW.GLFW_KEY_W;
		public static final int X = GLFW.GLFW_KEY_X;
		public static final int Y = GLFW.GLFW_KEY_Y;
		public static final int Z = GLFW.GLFW_KEY_Z;
	}

	public static void main(String mainClassName, String windowName) {
		EApplet mainClass;

		try {
			mainClass = (EApplet) Class.forName(mainClassName).newInstance();
		} catch (Exception e) {
			System.out.println("Class not found");
			e.printStackTrace();
			return;
		}
		mainClass.run(windowName);

	}

	protected long frames = 0;
	public int height;

	public boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
	public int mouseButton;

	public boolean mouseIsPressed;

	public static float mouseX;

	public static float mouseY;

	long startTime = System.currentTimeMillis();

	public int width;

	// The window handle
	protected long window;

	public double[] xPos = new double[1];

	public double[] yPos = new double[1];

	public void disableCursor() {
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
	}

	public void draw() {

	}

	public long getFrameRate() {
		return frames / Math.max(((System.currentTimeMillis() - startTime) / 1000), 1);
	}

	public void hideCursor() {
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
	}

	private void init(String windowName) {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(width, height, windowName, NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);

		glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				switch (action) {
				case GLFW.GLFW_PRESS:
					mouseIsPressed = true;
					Clicker.clickObjects();
					mouseClicked();
					break;
				case GLFW.GLFW_RELEASE:
					mouseIsPressed = false;
					Clicker.releaseObjects();
					mouseReleased();
					break;
				}
				mouseButton = button;
			}

		});

		glfwSetKeyCallback(window, new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {

				EApplet.this.keys[key] = true;

				switch (action) {
				case GLFW.GLFW_PRESS:
					EApplet.this.keys[key] = true;
					keyPressed();
					break;
				case GLFW.GLFW_RELEASE:
					EApplet.this.keys[key] = false;
					keyReleased();
					break;
				}

				if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
					glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
			}

		});
	}

	protected void keyPressed() {
	}

	public boolean keyPressed(int key) {
		return GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
	}

	protected void keyReleased() {
	}

	private void loop() {

		GL.createCapabilities();

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		mouseEvents();
		setup();

		while (!glfwWindowShouldClose(window)) {
			if (loop) {
				GL11.glClearColor(0, 0, 0, 0);
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); // clear the framebuffer
				GLFW.glfwGetCursorPos(window, xPos, yPos);
				mouseX = (float) xPos[0];
				mouseY = (float) (height - yPos[0]);

				draw();
				Hoverer.hoverObjects();
				Clicker.holdObjects();
				Drawer.drawObjects();

				glfwSwapBuffers(window);

				frames++;
			}
			glfwPollEvents();
		}

	}

	private boolean loop = true;

	public void noLoop() {
		loop = false;
	}

	public float map(float value, float min, float max, float newMin, float newMax) {
		return ((value - min) / (max - min)) * (newMax - newMin) + newMin;
	}

	public void mouseClicked() {
	}

	public void mouseEvents() {

	}

	public void mouseReleased() {
	}

	public float random() {
		return new Random().nextFloat();
	}

	public float random(float higher) {
		return random(0, higher);
	}

	public float random(float lower, float higher) {

		return new Random().nextFloat() * (higher - lower) + lower;

	}

	public float constrain(float x, float a, float b) {
		return Math.max(a, Math.min(x, b));
	}

	public void run(String windowName) {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		settings();
		Drawer.setup(width, height);
		init(windowName);
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public void settings() {

	}

	public void setup() {

	}

	public void showCursor() {
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
	}

	public void size(int _width, int _height) {
		width = _width;
		height = _height;
	}

}