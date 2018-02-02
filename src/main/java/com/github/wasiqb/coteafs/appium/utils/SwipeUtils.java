/**
 * Copyright (c) 2017, Wasiq Bhamla.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.wasiqb.coteafs.appium.utils;

import static java.time.Duration.ofSeconds;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import com.github.wasiqb.coteafs.appium.config.PlaybackSetting;
import com.github.wasiqb.coteafs.appium.device.SwipeDirection;
import com.github.wasiqb.coteafs.appium.device.SwipeStartPosition;

import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;

/**
 * @author wasiq.bhamla
 * @since Feb 1, 2018 12:23:46 PM
 */
public final class SwipeUtils {
	/**
	 * @author wasiq.bhamla
	 * @since Feb 2, 2018 3:25:54 PM
	 * @param driver
	 * @param setting
	 * @param fromElement
	 * @param toElement
	 * @return touch action
	 */
	public static TouchAction dragTo (final PerformsTouchActions driver, final PlaybackSetting setting,
			final MobileElement fromElement, final MobileElement toElement) {
		final TouchAction returnAction = new TouchAction (driver);
		returnAction.waitAction (ofSeconds (setting.getDelayBeforeSwipe ()))
			.press (fromElement)
			.moveTo (toElement)
			.waitAction (ofSeconds (setting.getDelayAfterSwipe ()))
			.release ();
		return returnAction;
	}

	/**
	 * @author wasiq.bhamla
	 * @since Feb 1, 2018 12:30:56 PM
	 * @param size
	 * @param location
	 * @param direction
	 * @param start
	 * @param distancePercent
	 * @param setting
	 * @param driver
	 * @param element
	 * @return touch action
	 */
	public static TouchAction swipeTo (final Dimension size, final Point location, final SwipeDirection direction,
			final SwipeStartPosition start, final int distancePercent, final PlaybackSetting setting,
			final PerformsTouchActions driver, final MobileElement element) {
		print (size);
		print (location);
		final double distance = distancePercent / 100.0;
		final int w = size.getWidth ();
		final int h = size.getHeight ();

		final Point startPosition = getStartPoint (start, w, h, location);
		print (startPosition);
		final int startX = startPosition.getX ();
		final int startY = startPosition.getY ();

		final int endX = (int) (startX * direction.getX () * distance);
		final int endY = (int) (startY * direction.getY () * distance);
		print (endX);
		print (endY);

		final int beforeSwipe = setting.getDelayBeforeSwipe ();
		final int afterSwipe = setting.getDelayAfterSwipe ();
		final TouchAction returnAction = new TouchAction (driver);
		if (element == null) {
			returnAction.waitAction (ofSeconds (beforeSwipe))
				.press (startX, startY)
				.moveTo (endX, endY)
				.waitAction (ofSeconds (afterSwipe))
				.release ();
		}
		else {
			returnAction.waitAction (ofSeconds (beforeSwipe))
				.press (element, startX, startY)
				.moveTo (element, startX + endX, startY + endY)
				.waitAction (ofSeconds (afterSwipe))
				.release ();
		}

		return returnAction;
	}

	private static Point getStartPoint (final SwipeStartPosition start, final int w, final int h,
			final Point location) {
		int x = 0;
		int y = 0;
		switch (start) {
			case BOTTOM:
				x = w / 2;
				y = h - 5;
				break;
			case CENTER:
				x = w / 2;
				y = h / 2;
				break;
			case LEFT:
				x += 5;
				y = h / 2;
				break;
			case RIGHT:
				x = w - 5;
				y = h / 2;
				break;
			case TOP:
			default:
				x = w / 2;
				y += 5;
				break;
		}
		return new Point (x, y);
	}

	/**
	 * @author wasiq.bhamla
	 * @since Feb 1, 2018 3:19:17 PM
	 * @param size
	 */
	private static void print (final Object obj) {
		System.out.println (obj);
	}

	private SwipeUtils () {
		// Util class.
	}
}