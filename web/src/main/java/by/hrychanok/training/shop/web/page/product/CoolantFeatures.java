package by.hrychanok.training.shop.web.page.product;

import java.util.Arrays;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.RangeValidator;
import com.googlecode.wicket.kendo.ui.form.TextField;
import by.hrychanok.training.shop.model.Coolant;
import by.hrychanok.training.shop.model.CoolantColor;
import by.hrychanok.training.shop.model.FluidState;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.service.GenericProductService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.page.common.CoolantColorChoiceRenderer;
import by.hrychanok.training.shop.web.page.common.FluidStateChoiceRenderer;

@AuthorizeAction(roles = { "admin" }, action = "ENABLE")
public class CoolantFeatures extends ProductFormPanel<Coolant> {

	@SpringBean
	GenericProductService<Coolant> genericProductService;

	boolean visibleForOnlyAdmin = AuthorizedSession.get().isSignedIn()
			&& AuthorizedSession.get().getLoggedUser().getRole().equals(UserRole.admin);

	private Coolant coolant;

	public CoolantFeatures(String id,  IModel<Coolant> model) {
		super(id, model);
		coolant = (Coolant) model.getObject();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		// DropDownChoice destination Tire //
		final DropDownChoice<FluidState> fluidState = new DropDownChoice<>("fluidState",
				Arrays.asList(FluidState.values()), FluidStateChoiceRenderer.INSTANCE);
		fluidState.setRequired(true);
		add(fluidState);

		// DropDownChoice Season Tire //
		final DropDownChoice<CoolantColor> coolantColor = new DropDownChoice<>("color", Arrays.asList(CoolantColor.values()),
				CoolantColorChoiceRenderer.INSTANCE);
		coolantColor.setRequired(true);
		add(coolantColor);

		TextField<Integer> volume = new TextField<>("volume");
		volume.add(RangeValidator.<Integer> range(1, 20));
		volume.setRequired(true);
		add(volume);
	}
}
