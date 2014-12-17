package hr.nhex.graphic.mouse;

import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.adapters.AdapterType;
import hr.nhex.graphic.mouse.resolvers.AbstractMouseResolver;
import hr.nhex.graphic.mouse.resolvers.MovementResolver;
import hr.nhex.graphic.mouse.resolvers.PlacementResolver;
import hr.nhex.graphic.mouse.resolvers.PushResolver;
import hr.nhex.graphic.mouse.resolvers.RotateResolver;

import java.util.ArrayList;
import java.util.List;

public class MouseResolverTree {

	private List<AbstractMouseResolver> resolverList = new ArrayList<AbstractMouseResolver>();

	private int currentActiveResolver = 0;

	public MouseResolverTree(NeuroshimaCanvas cn) {
		resolverList.add(new PlacementResolver(cn));
		resolverList.add(new RotateResolver(cn));
		resolverList.add(new PushResolver(cn));
		resolverList.add(new MovementResolver(cn));

	}

	public AbstractMouseResolver getActiveResolver() {
		return resolverList.get(currentActiveResolver);
	}

	public void setActiveResolver(AdapterType type) {

		for (int i = 0; i < resolverList.size(); i++) {
			AbstractMouseResolver r = resolverList.get(i);
			if (r.getType() == type) {
				currentActiveResolver = i;
				return;
			}
		}
	}

}
