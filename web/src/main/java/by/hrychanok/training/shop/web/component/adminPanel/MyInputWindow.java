package by.hrychanok.training.shop.web.component.adminPanel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.widget.window.InputWindow;

public abstract class MyInputWindow extends InputWindow<String> {
	private static final long serialVersionUID = 1L;

	public  MyInputWindow(String id) {
		super(id, "My Input Window", Model.of(""), "Please provide a value:");
	}
}