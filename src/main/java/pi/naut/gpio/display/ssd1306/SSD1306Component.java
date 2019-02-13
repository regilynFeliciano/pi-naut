package pi.naut.gpio.display.ssd1306;

import io.micronaut.core.util.CollectionUtils;
import io.micronaut.core.util.StringUtils;
import net.fauxpark.oled.SSD1306;
import net.fauxpark.oled.font.CodePage1252;

import java.util.List;

import static pi.naut.gpio.display.ssd1306.SSD1306Constants.*;

public class SSD1306Component {

	public void titleBar(SSD1306 controller, String title) {
		if (StringUtils.isNotEmpty(title)) {
			controller.getGraphics().text(25, MIN_XY, new CodePage1252(), title);
			controller.getGraphics().line(MIN_XY, TEXT_HEIGHT, MAX_X, TEXT_HEIGHT);
		}
	}

	public void scrollableList(SSD1306 controller, List<Item> items) {
		int bufferCount;
		if (items.size() < 3) {
			bufferCount = items.size();
		} else {
			bufferCount = 3;
		}

		// TODO, add logic to automatically hide/show arrows
		bufferUpArrow(controller);
		for (int i = 0; i < bufferCount; i++) {
			controller.getGraphics().text((RADIUS_RADIO_SELECTED * 2) + (PADDING * 2), TEXT_HEIGHT + (TEXT_HEIGHT * (i + 1)) + 1, new CodePage1252(), items.get(i).getContent());
			if (items.get(i).isSelected()) {
				controller.getGraphics().circle(RADIUS_RADIO_SELECTED, (TEXT_HEIGHT + (PADDING * 2)) + (TEXT_HEIGHT * (i + 1)), 1);
			}
		}
		bufferDownArrow(controller);
	}

	public void bufferDownArrow(SSD1306 controller) {
		controller.getGraphics().line(HALF_WIDTH - ARROW_SLOPE, BASE_HEIGHT_ARROW_DOWN, HALF_WIDTH, BASE_HEIGHT_ARROW_DOWN + ARROW_SLOPE);
		controller.getGraphics().line(HALF_WIDTH, BASE_HEIGHT_ARROW_DOWN + ARROW_SLOPE, HALF_WIDTH + ARROW_SLOPE, BASE_HEIGHT_ARROW_DOWN);
	}

	public void bufferUpArrow(SSD1306 controller) {
		controller.getGraphics().line(HALF_WIDTH - ARROW_SLOPE, BASE_HEIGHT_ARROW_UP + ARROW_SLOPE, HALF_WIDTH, BASE_HEIGHT_ARROW_UP);
		controller.getGraphics().line(HALF_WIDTH, BASE_HEIGHT_ARROW_UP, HALF_WIDTH + ARROW_SLOPE, BASE_HEIGHT_ARROW_UP + ARROW_SLOPE);
	}

	public void actionBar(SSD1306 controller, List<Action> actions) {
		if (CollectionUtils.isNotEmpty(actions)) {
			int i = 1;
			int size = actions.size();
			int buttonWidth = (MAX_WIDTH / size);

			for (Action action : actions) {
				// button box
				controller.getGraphics().rectangle(
						(buttonWidth * (i - 1)) + (i - 1),
						MAX_HEIGHT - HEIGHT_BUTTON,
						buttonWidth,
						HEIGHT_BUTTON,
						false
				);

				// button text
				controller.getGraphics().text(
						(buttonWidth * (i - 1)) + (i - 1) + PADDING,
						MAX_HEIGHT - FONT_HEIGHT - PADDING,
						new CodePage1252(),
						action.getName()
				);

				// selected indicator
				if (action.isSelected()) {
					controller.getGraphics().circle(
							(buttonWidth * i) - (RADIUS_RADIO_SELECTED * 2) - PADDING,
							MAX_HEIGHT - (HEIGHT_BUTTON / 2) - 1,
							RADIUS_RADIO_SELECTED
					);
				}

				i++;
			}
		}
	}

}
