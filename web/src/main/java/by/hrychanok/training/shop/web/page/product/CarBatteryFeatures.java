package by.hrychanok.training.shop.web.page.product;

import java.util.Arrays;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.RangeValidator;
import com.googlecode.wicket.kendo.ui.form.TextField;
import by.hrychanok.training.shop.model.BatteryType;
import by.hrychanok.training.shop.model.CarBattery;
import by.hrychanok.training.shop.model.Polarity;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.service.GenericProductService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.page.common.BatteryTypeChoiceRenderer;
import by.hrychanok.training.shop.web.page.common.PolarityChoiceRenderer;

@AuthorizeAction(roles = { "admin" }, action = "ENABLE")
public class CarBatteryFeatures extends ProductFormPanel<CarBattery> {

	@SpringBean
	GenericProductService<CarBattery> genericProductService;

	boolean visibleForOnlyAdmin = AuthorizedSession.get().isSignedIn()
			&& AuthorizedSession.get().getLoggedUser().getRole().equals(UserRole.admin);

	private CarBattery tire;

	public CarBatteryFeatures(String id, IModel<CarBattery> model) {
		super(id, model);
		tire = (CarBattery) model.getObject();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		// DropDownChoice destination Tire //
		final DropDownChoice<BatteryType> batteryType = new DropDownChoice<>("batteryType",
				Arrays.asList(BatteryType.values()), BatteryTypeChoiceRenderer.INSTANCE);
		batteryType.setRequired(true);
		add(batteryType);

		TextField<Integer> voltage = new TextField<>("voltage");
		voltage.add(RangeValidator.<Integer> range(6, 12));
		voltage.setRequired(true);
		add(voltage);

		TextField<Integer> capacity = new TextField<>("capacity");
		capacity.add(RangeValidator.<Integer> range(32, 235));
		capacity.setRequired(true);
		add(capacity);

		TextField<Integer> cca = new TextField<>("cca");
		cca.add(RangeValidator.<Integer> range(0, 1600));
		cca.setRequired(true);
		add(cca);

		// DropDownChoice Season Tire //
		final DropDownChoice<Polarity> polarity = new DropDownChoice<>("polarity", Arrays.asList(Polarity.values()),
				PolarityChoiceRenderer.INSTANCE);
		polarity.setRequired(true);
		add(polarity);

	}
}
