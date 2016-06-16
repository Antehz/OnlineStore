package by.hrychanok.training.shop.web.page.product;

import java.util.Arrays;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.RangeValidator;
import com.googlecode.wicket.kendo.ui.form.TextField;
import by.hrychanok.training.shop.model.Lamp;
import by.hrychanok.training.shop.model.LampType;
import by.hrychanok.training.shop.service.GenericProductService;
import by.hrychanok.training.shop.web.page.common.LampTypeChoiceRenderer;

@AuthorizeAction(roles = { "admin" }, action = "ENABLE")
public class LampFeatures extends ProductFormPanel<Lamp> {

	@SpringBean
	GenericProductService<Lamp> genericProductService;

	private Lamp lamp;

	public LampFeatures(String id, IModel<Lamp> model) {
		super(id, model);
		lamp = (Lamp) model.getObject();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		// DropDownChoice Season Tire //
		final DropDownChoice<LampType> lampType = new DropDownChoice<>("lampType", Arrays.asList(LampType.values()),
				LampTypeChoiceRenderer.INSTANCE);
		lampType.setRequired(true);
		add(lampType);

		TextField<String> countryManufacturer = new TextField<>("countryManufacturer");
		countryManufacturer.setRequired(true);
		add(countryManufacturer);

		TextField<String> cap = new TextField<>("cap");
		cap.setRequired(true);
		add(cap);

		TextField<Integer> power = new TextField<>("power");
		power.add(RangeValidator.<Integer> range(0, 300));
		power.setRequired(true);
		add(power);

		TextField<Integer> temperature = new TextField<>("temperature");
		temperature.add(RangeValidator.<Integer> range(1500, 25000));
		temperature.setRequired(true);
		add(temperature);

		TextField<Integer> luminousFlux = new TextField<>("luminousFlux");
		luminousFlux.add(RangeValidator.<Integer> range(0, 35000));
		luminousFlux.setRequired(true);
		add(luminousFlux);

		TextField<Integer> packageContents = new TextField<>("packageContents");
		packageContents.add(RangeValidator.<Integer> range(1, 10));
		packageContents.setRequired(true);
		add(packageContents);
	}
}
