/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason AgentSpeak(L++)                                #
 * # Copyright (c) 2015-19, LightJason (info@lightjason.org)                            #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

package org.lightjason.agentspeak.action.bit;

import org.junit.Assert;
import org.junit.Test;


/**
 * test bit generic calls
 */
public final class TestCActionMathBit
{

    /**
     * test blas type string call
     */
    @Test
    public void blastype()
    {
        Assert.assertEquals( EBlasType.DENSE, EBlasType.of( "dense" ) );
        Assert.assertEquals( EBlasType.SPARSE, EBlasType.of( "sparse" ) );
    }

    /**
     * test blas type exists
     */
    @Test
    public void blastypeexists()
    {
        Assert.assertFalse( EBlasType.exists( "xxx" ) );
        Assert.assertTrue( EBlasType.exists( "sparse" ) );
        Assert.assertTrue( EBlasType.exists( "dense" ) );
    }

}
