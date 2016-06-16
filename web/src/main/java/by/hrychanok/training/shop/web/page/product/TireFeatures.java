package by.hrychanok.training.shop.web.page.product;

import java.util.Arrays;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.RangeValidator;
import com.googlecode.wicket.kendo.ui.form.CheckBox;
import com.googlecode.wicket.kendo.ui.form.TextField;
import by.hrychanok.training.shop.model.Season;
import by.hrychanok.training.shop.model.Tire;
import by.hrychanok.training.shop.model.TireDestination;
import by.hrychanok.training.shop.model.UserRole;
import by.hrychanok.training.shop.service.GenericProductService;
import by.hrychanok.training.shop.web.app.AuthorizedSession;
import by.hrychanok.training.shop.web.page.common.SeasonChoiceRenderer;
import by.hrychanok.training.shop.web.page.common.TireDestinationChoiceRenderer;

@AuthorizeAction(roles = { "admin" }, action = "ENABLE")
public class TireFeatures extends ProductFormPanel<Tire> {

	@SpringBean
	GenericProductService<Tire> genericProductService;

	boolean visibleForOnlyAdmin = AuthorizedSession.get().isSignedIn()
			&& AuthorizedSession.get().getLoggedUser().getRole().equals(UserRole.admin);

	private Tire tire;

	public TireFeatures(String id, IModel<Tire> model) {
		super(id, model);
		tire = (Tire) model.getObject();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		// DropDownChoice destination Tire //
		final DropDownChoice<TireDestination> destination = new DropDownChoice<>("tireDestination",
				Arrays.asList(TireDestination.values()), TireDestinationChoiceRenderer.INSTANCE);
		destination.setRequired(true);
		add(destination);

		// DropDownChoice Season Tire //
		final DropDownChoice<Season> season = new DropDownChoice<>("tireSeason", Arrays.asList(Season.values()),
				SeasonChoiceRenderer.INSTANCE);
		season.setRequired(true);
		add(season);

		TextField<Integer> profileWidth = new TextField<>("profileWidth");
		profileWidth.add(RangeValidator.<Integer> range(135, 1050));
		profileWidth.setRequired(true);
		add(profileWidth);

		TextField<Integer> profileHeight = new TextField<>("profileHeight");
		profileHeight.add(RangeValidator.<Integer> range(15, 95));
		profileHeight.setRequired(true);
		add(profileHeight);

		TextField<Integer> rimSize = new TextField<>("rimSize");
		rimSize.add(RangeValidator.<Integer> range(8, 52));
		rimSize.setRequired(true);
		add(rimSize);

		CheckBox spikes = new CheckBox("spikes");
		add(spikes);

	}
}
