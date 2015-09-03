package org.opengis.cite.indoorgml10.level2;

import org.opengis.cite.indoorgml10.CommonFixture;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Includes various tests of capability 2.
 */
public class Capability2Tests extends CommonFixture {

	/**
     * Run conformance level 2 tests only if the preconditions are satisfied.
     */
    @BeforeTest
    public void checkPreconditions() {
        Assert.assertTrue(2 > 1,
                "Preconditions for Conformance Level 2 were not satisfied.");
    }

    /**
     * Checks the result of the length function.
     */
    @Test(description = "Implements ATC 2-1")
    public void checkLength() {
        String str = "perihelion";
        Assert.assertEquals(str.length(), 10);
    }

    /**
     * Checks the Unicode code point value of the first character.
     */
    @Test(description = "Implements ATC 2-2")
    public void codePoint() {
        String str = "perihelion";
        Assert.assertEquals(str.codePointAt(0), 100);
    }
}
