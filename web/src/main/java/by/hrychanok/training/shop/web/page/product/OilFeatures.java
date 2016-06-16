package by.hrychanok.training.shop.web.page.product;

import java.util.Arrays;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.RangeValidator;
import com.googlecode.wicket.kendo.ui.form.TextField;
import by.hrychanok.training.shop.model.Oil;
import by.hrychanok.training.shop.model.OilComposition;
import by.hrychanok.training.shop.model.OilDestination;
import by.hrychanok.training.shop.model.OilType;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.service.GenericProductService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.page.common.OilCompositionChoiceRenderer;
import by.hrychanok.training.shop.web.page.common.OilDestinationChoiceRenderer;
import by.hrychanok.training.shop.web.page.common.OilTypeChoiceRenderer;

@AuthorizeAction(roles = { "admin" }, action = "ENABLE")
public class OilFeatures extends ProductFormPanel<Oil> {

	@SpringBean
	GenericProductService<Oil> genericProductService;

	boolean visibleForOnlyAdmin = AuthorizedSession.get().isSignedIn()
			&& AuthorizedSession.get().getLoggedUser().getRole().equals(UserRole.admin);

	private Oil oil;

	public OilFeatures(String id, IModel<Oil> model) {
		super(id, model);
		oil = (Oil) model.getObject();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		// DropDownChoice destination Tire //
		final DropDownChoice<OilType> oilType = new DropDownChoice<>("oilType", Arrays.asList(OilType.values()),
				OilTypeChoiceRenderer.INSTANCE);
		oilType.setRequired(true);
		add(oilType);

		// DropDownChoice Season Tire //
		final DropDownChoice<OilComposition> oilComposition = new DropDownChoice<>("oilComposition",
				Arrays.asList(OilComposition.values()), OilCompositionChoiceRenderer.INSTANCE);
		oilComposition.setRequired(true);
		add(oilComposition);

		TextField<String> viscosity = new TextField<>("viscosity");
		viscosity.setRequired(true);
		add(viscosity);

		// DropDownChoice Season Tire //
		final DropDownChoice<OilDestination> oilDestination = new DropDownChoice<>("oilDestination",
				Arrays.asList(OilDestination.values()), OilDestinationChoiceRenderer.INSTANCE);
		oilDestination.setRequired(true);
		add(oilDestination);

		TextField<Integer> volume = new TextField<>("volume");
		volume.add(RangeValidator.<Integer> range(0, 100));
		volume.setRequired(true);
		add(volume);

	}
}
