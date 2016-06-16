package by.hrychanok.training.shop.web.page.product;

import java.util.Arrays;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.RangeValidator;
import com.googlecode.wicket.kendo.ui.form.TextField;
import by.hrychanok.training.shop.model.FluidState;
import by.hrychanok.training.shop.model.ScreenWash;
import by.hrychanok.training.shop.model.Season;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.service.GenericProductService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.page.common.FluidStateChoiceRenderer;
import by.hrychanok.training.shop.web.page.common.SeasonChoiceRenderer;

@AuthorizeAction(roles = { "admin" }, action = "ENABLE")
public class ScreenWashFeatures extends ProductFormPanel<ScreenWash> {

	@SpringBean
	GenericProductService<ScreenWash> genericProductService;

	boolean visibleForOnlyAdmin = AuthorizedSession.get().isSignedIn()
			&& AuthorizedSession.get().getLoggedUser().getRole().equals(UserRole.admin);

	private ScreenWash screenWash;

	public ScreenWashFeatures(String id, IModel<ScreenWash> model) {
		super(id, model);
		screenWash = (ScreenWash) model.getObject();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final DropDownChoice<FluidState> fluidState = new DropDownChoice<>("fluidState",
				Arrays.asList(FluidState.values()), FluidStateChoiceRenderer.INSTANCE);
		fluidState.setRequired(true);
		add(fluidState);

		final DropDownChoice<Season> season = new DropDownChoice<>("washSeason", Arrays.asList(Season.values()),
				SeasonChoiceRenderer.INSTANCE);
		season.setRequired(true);
		add(season);

		TextField<Integer> temperature = new TextField<>("temperature");
		temperature.add(RangeValidator.<Integer> range(-50, 100));
		temperature.setRequired(true);
		add(temperature);

		TextField<String> countryManufacturer = new TextField<>("countryManufacturer");
		countryManufacturer.setRequired(true);
		add(countryManufacturer);

		TextField<Integer> volume = new TextField<>("volume");
		volume.add(RangeValidator.<Integer> range(1, 20));
		volume.setRequired(true);
		add(volume);

	}
}
