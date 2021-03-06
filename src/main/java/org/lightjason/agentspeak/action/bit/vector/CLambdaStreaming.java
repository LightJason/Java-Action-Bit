/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason                                                #
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

package org.lightjason.agentspeak.action.bit.vector;

import cern.colt.matrix.tbit.BitVector;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.lightjason.agentspeak.action.IBaseLambdaStreaming;

import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * streaming a bit vector
 */
public final class CLambdaStreaming extends IBaseLambdaStreaming<BitVector>
{
    /**
     * serial id
     */
    private static final long serialVersionUID = 3608586892880271763L;

    @Override
    public Stream<?> apply( final BitVector p_vector )
    {
        return IntStream.range( 0, p_vector.size() ).boxed().map( i -> p_vector.getQuick( i ) ? 1 : 0 );
    }

    @NonNull
    @Override
    public Stream<Class<?>> assignable()
    {
        return Stream.of( BitVector.class );
    }
}
