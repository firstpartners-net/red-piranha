package net.firstpartners.data;

import org.kie.api.project.KieActivator;

import net.firstpartners.data.Cell;

/*
 * Class needed so that Kogito DMN Editor can pickup on Data classes
 */
@KieActivator
public class Activator {

    // reference to remove unused import error
    static Cell myCell;
}
