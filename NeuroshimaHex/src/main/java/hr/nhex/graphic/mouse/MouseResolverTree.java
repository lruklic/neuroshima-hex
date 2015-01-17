package hr.nhex.graphic.mouse;

import hr.nhex.graphic.canvas.NeuroshimaCanvas;
import hr.nhex.graphic.mouse.adapters.AdapterType;
import hr.nhex.graphic.mouse.resolvers.AbstractMouseResolver;
import hr.nhex.graphic.mouse.resolvers.MedicResolver;
import hr.nhex.graphic.mouse.resolvers.MovementResolver;
import hr.nhex.graphic.mouse.resolvers.PlacementResolver;
import hr.nhex.graphic.mouse.resolvers.PushResolver;
import hr.nhex.graphic.mouse.resolvers.RotateResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Class that represents a data structure that holds all the mouse resolvers for Neuroshima game.</p>
 *
 * <p>Inside MouseResolverTree there are 5 resolvers:
 * <ul>
 * 	<li> PlacementResolver, resolves general mouse events that happen on board
 * 	<li> RotateResolver, resolves rotation of tiles when placed on board
 * 	<li> PushResolver, resolves push action when one unit pushes the other
 *  <li> MovementResolver, resolves movements of units
 *  <li> MedicResolver, resolves medic action when unit is being healed
 * </ul>
 *
 * <p> At all times, one of the following resolvers is active and is used to resolve current user action. </p>
 *
 * @author Luka Ruklic
 *
 */

public class MouseResolverTree {

	/**
	 * List that contains all resolvers that exist in current context.
	 */
	private List<AbstractMouseResolver> resolverList = new ArrayList<AbstractMouseResolver>();
	/**
	 * Number that determines which resolver is currently active.
	 */
	private Integer currentActiveResolver = 0;
	/**
	 * Constructor.
	 *
	 * @param cn canvas of the current game
	 */
	public MouseResolverTree(NeuroshimaCanvas cn) {
		resolverList.add(new PlacementResolver(cn));
		resolverList.add(new RotateResolver(cn));
		resolverList.add(new PushResolver(cn));
		resolverList.add(new MovementResolver(cn));
		resolverList.add(new MedicResolver(cn));

	}
	/**
	 * Getter for an active resolver.
	 *
	 * @return active resolver
	 */
	public AbstractMouseResolver getActiveResolver() {
		return resolverList.get(currentActiveResolver);
	}
	/**
	 * Method that disables all the resolvers.
	 */
	public void disableAllResolvers() {
		currentActiveResolver = null;
	}
	/**
	 * Method that sets <code>type</code> resolver as active.
	 *
	 * @param type type of the resolver that is being setted as active
	 */
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
