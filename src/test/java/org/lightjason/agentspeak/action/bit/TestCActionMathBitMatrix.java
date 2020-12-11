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

package org.lightjason.agentspeak.action.bit;

import cern.colt.matrix.tbit.BitMatrix;
import cern.colt.matrix.tbit.BitVector;
import cern.colt.matrix.tdouble.DoubleMatrix2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.action.bit.matrix.CAnd;
import org.lightjason.agentspeak.action.bit.matrix.CBoolValue;
import org.lightjason.agentspeak.action.bit.matrix.CColumn;
import org.lightjason.agentspeak.action.bit.matrix.CColumns;
import org.lightjason.agentspeak.action.bit.matrix.CCopy;
import org.lightjason.agentspeak.action.bit.matrix.CCreate;
import org.lightjason.agentspeak.action.bit.matrix.CDimension;
import org.lightjason.agentspeak.action.bit.matrix.CFalseCount;
import org.lightjason.agentspeak.action.bit.matrix.CHammingDistance;
import org.lightjason.agentspeak.action.bit.matrix.CLambdaStreaming;
import org.lightjason.agentspeak.action.bit.matrix.CNAnd;
import org.lightjason.agentspeak.action.bit.matrix.CNot;
import org.lightjason.agentspeak.action.bit.matrix.CNumericValue;
import org.lightjason.agentspeak.action.bit.matrix.COr;
import org.lightjason.agentspeak.action.bit.matrix.CRow;
import org.lightjason.agentspeak.action.bit.matrix.CRows;
import org.lightjason.agentspeak.action.bit.matrix.CSet;
import org.lightjason.agentspeak.action.bit.matrix.CSize;
import org.lightjason.agentspeak.action.bit.matrix.CToBlas;
import org.lightjason.agentspeak.action.bit.matrix.CToVector;
import org.lightjason.agentspeak.action.bit.matrix.CTrueCount;
import org.lightjason.agentspeak.action.bit.matrix.CXor;
import org.lightjason.agentspeak.error.context.CExecutionIllegealArgumentException;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ITerm;
import org.lightjason.agentspeak.language.execution.IContext;
import org.lightjason.agentspeak.language.execution.IExecution;
import org.lightjason.agentspeak.testing.IBaseTest;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * test math bit matrix functions
 */
public final class TestCActionMathBitMatrix extends IBaseTest
{
    /**
     * testing matrix
     * @note static because of usage in data-provider and test-initialize
     */
    private static final BitMatrix MATRIX1 = new BitMatrix( 2, 2 );
    /**
     * testing matrix
     * @note static because of usage in data-provider and test-initialize
     */
    private static final BitMatrix MATRIX2 = new BitMatrix( 2, 2 );


    /**
     * initialize
     */
    @BeforeAll
    public static void initialize()
    {
        MATRIX1.put( 0, 0, true );
        MATRIX1.put( 1, 0, false );
        MATRIX1.put( 0, 1, false );
        MATRIX1.put( 1, 1, true );

        MATRIX2.put( 0, 0, false );
        MATRIX2.put( 1, 0, true );
        MATRIX2.put( 0, 1, true );
        MATRIX2.put( 1, 1, true );
    }

    /**
     * data provider generator
     * @return data
     */
    public static Stream<Arguments> generator()
    {
        return Stream.of(

                Arguments.of( Stream.of( MATRIX1, MATRIX2 ).map( CRawTerm::of ).collect( Collectors.toList() ), CColumns.class, Stream.of( 2D, 2D ) ),
                Arguments.of( Stream.of( MATRIX1, MATRIX2 ).map( CRawTerm::of ).collect( Collectors.toList() ), CFalseCount.class, Stream.of( 2D, 1D ) ),
                Arguments.of( Stream.of( MATRIX1, MATRIX2 ).map( CRawTerm::of ).collect( Collectors.toList() ), CDimension.class, Stream.of( 2D, 2D, 2D, 2D ) ),
                Arguments.of( Stream.of( MATRIX1, MATRIX2 ).map( CRawTerm::of ).collect( Collectors.toList() ), CCopy.class, Stream.of( MATRIX1, MATRIX2 ) ),
                Arguments.of( Stream.of( MATRIX1, MATRIX2 ).map( CRawTerm::of ).collect( Collectors.toList() ), CTrueCount.class, Stream.of( 2D, 3D ) ),
                Arguments.of( Stream.of( MATRIX1, MATRIX2 ).map( CRawTerm::of ).collect( Collectors.toList() ), CSize.class, Stream.of( 4, 4 ) ),
                Arguments.of( Stream.of( MATRIX1, MATRIX2 ).map( CRawTerm::of ).collect( Collectors.toList() ), CRows.class, Stream.of( 2D, 2D ) ),
                Arguments.of( Stream.of( MATRIX1, MATRIX2 ).map( CRawTerm::of ).collect( Collectors.toList() ), CNot.class, Stream.empty() ),
                Arguments.of( Stream.of( MATRIX1, MATRIX2 ).map( CRawTerm::of ).collect( Collectors.toList() ), COr.class, Stream.empty() ),
                Arguments.of( Stream.of( MATRIX1, MATRIX2 ).map( CRawTerm::of ).collect( Collectors.toList() ), CAnd.class, Stream.empty() ),
                Arguments.of( Stream.of( MATRIX1, MATRIX2 ).map( CRawTerm::of ).collect( Collectors.toList() ), CXor.class, Stream.empty() ),
                Arguments.of( Stream.of( MATRIX1, MATRIX2 ).map( CRawTerm::of ).collect( Collectors.toList() ), CNAnd.class, Stream.empty() ),
                Arguments.of( Stream.of( MATRIX1, MATRIX2 ).map( CRawTerm::of ).collect( Collectors.toList() ), CHammingDistance.class, Stream.of( 3D ) )

        );
    }

    /**
     * test all input actions
     *
     * @param p_input input data
     * @param p_action action class
     * @param p_result result
     * @throws IllegalAccessException is thrwon on instantiation error
     * @throws InstantiationException is thrwon on instantiation error
     * @throws NoSuchMethodException is thrwon on instantiation error
     * @throws InvocationTargetException is thrwon on instantiation error
     */
    @ParameterizedTest
    @MethodSource( "generator" )
    public void action( @Nonnull final List<ITerm> p_input, @Nonnull final Class<? extends IAction> p_action, @Nonnull final Stream<Object> p_result )
        throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException
    {
        final List<ITerm> l_return = new ArrayList<>();

        p_action.getConstructor().newInstance().execute(
            false,
            IContext.EMPTYPLAN,
            p_input,
            l_return
        );

        Assertions.assertArrayEquals(
                p_result.toArray(),
                l_return.stream().map( ITerm::raw ).toArray(),
                p_action.toGenericString()
        );
    }

    /**
     * test create
     */
    @Test
    public void create()
    {
        final List<ITerm> l_return = new ArrayList<>();

        new CCreate().execute(
            false, IContext.EMPTYPLAN,
            Stream.of( 2, 2 ).map( CRawTerm::of ).collect( Collectors.toList() ),
            l_return
        );

        Assertions.assertEquals( 1, l_return.size() );
        Assertions.assertTrue( l_return.get( 0 ).raw() instanceof BitMatrix );
        Assertions.assertEquals( 4, l_return.get( 0 ).<BitMatrix>raw().size() );
        Assertions.assertEquals( 2, l_return.get( 0 ).<BitMatrix>raw().rows() );
        Assertions.assertEquals( 2, l_return.get( 0 ).<BitMatrix>raw().columns() );
    }

    /**
     * test toBitVector
     */
    @Test
    public void tobitvector()
    {
        final List<ITerm> l_return = new ArrayList<>();

        new CToVector().execute(
            false, IContext.EMPTYPLAN,
            Stream.of( MATRIX2 ).map( CRawTerm::of ).collect( Collectors.toList() ),
            l_return
        );

        Assertions.assertEquals( l_return.size(), 1 );
        Assertions.assertTrue( l_return.get( 0 ).raw() instanceof BitVector );
        Assertions.assertEquals( l_return.get( 0 ).<BitVector>raw().size(), 4 );

        final BitVector l_bitvector = l_return.get( 0 ).raw();

        Assertions.assertTrue( l_bitvector.get( 0 ) );
        Assertions.assertTrue( l_bitvector.get( 1 ) );
        Assertions.assertTrue( l_bitvector.get( 2 ) );
        Assertions.assertFalse( l_bitvector.get( 3 ) );
    }

    /**
     * test column
     */
    @Test
    public void column()
    {
        final List<ITerm> l_return = new ArrayList<>();

        new CColumn().execute(
            false, IContext.EMPTYPLAN,
            Stream.of( 1, MATRIX2 ).map( CRawTerm::of ).collect( Collectors.toList() ),
            l_return
        );

        Assertions.assertEquals( l_return.size(), 1 );
        Assertions.assertTrue( l_return.get( 0 ).raw() instanceof BitVector );
        Assertions.assertEquals( l_return.get( 0 ).<BitVector>raw().size(), 2 );

        final BitVector l_bitvector = l_return.get( 0 ).raw();

        Assertions.assertTrue( l_bitvector.get( 0 ) );
        Assertions.assertTrue( l_bitvector.get( 1 ) );
    }

    /**
     * test row
     */
    @Test
    public void row()
    {
        final List<ITerm> l_return = new ArrayList<>();

        new CRow().execute(
            false, IContext.EMPTYPLAN,
            Stream.of( 1, MATRIX2 ).map( CRawTerm::of ).collect( Collectors.toList() ),
            l_return
        );

        Assertions.assertEquals( l_return.size(), 1 );
        Assertions.assertTrue( l_return.get( 0 ).raw() instanceof BitVector );
        Assertions.assertEquals( l_return.get( 0 ).<BitVector>raw().size(), 2 );

        final BitVector l_bitvector = l_return.get( 0 ).raw();

        Assertions.assertTrue( l_bitvector.get( 0 ) );
        Assertions.assertTrue( l_bitvector.get( 1 ) );
    }

    /**
     * test numericvalue
     */
    @Test
    public void numericvalue()
    {
        final List<ITerm> l_return = new ArrayList<>();

        new CNumericValue().execute(
            false, IContext.EMPTYPLAN,
            Stream.of( MATRIX1, 1, 0 ).map( CRawTerm::of ).collect( Collectors.toList() ),
            l_return
        );

        Assertions.assertEquals( 0D, l_return.get( 0 ).<Number>raw() );
    }

    /**
     * test numeric value error
     */
    @Test
    public void numericvalueerror()
    {
        Assertions.assertThrows(
            CExecutionIllegealArgumentException.class,
            () -> new CNumericValue().execute(
                false, IContext.EMPTYPLAN,
                Collections.emptyList(),
                Collections.emptyList()
            )
        );
    }

    /**
     * test boolean value
     */
    @Test
    public void boolValue()
    {
        final List<ITerm> l_return = new ArrayList<>();

        new CBoolValue().execute(
            false, IContext.EMPTYPLAN,
            Stream.of( MATRIX2, 0, 0 ).map( CRawTerm::of ).collect( Collectors.toList() ),
            l_return
        );

        Assertions.assertEquals( false, l_return.get( 0 ).<Boolean>raw() );
    }

    /**
     * test boolean value error
     */
    @Test
    public void boolValueError()
    {
        Assertions.assertThrows(
            CExecutionIllegealArgumentException.class,
            () -> new CBoolValue().execute(
                false, IContext.EMPTYPLAN,
                Stream.of( MATRIX2, 0 ).map( CRawTerm::of ).collect( Collectors.toList() ),
                Collections.emptyList()
            )
        );
    }

     /**
     * test toblas
     */
    @Test
    public void toblas()
    {
        final List<ITerm> l_return = new ArrayList<>();
        final IExecution l_toblas = new CToBlas();

        l_toblas.execute(
            false, IContext.EMPTYPLAN,
            Stream.of( MATRIX1, "dense" ).map( CRawTerm::of ).collect( Collectors.toList() ),
            l_return
        );

        l_toblas.execute(
            false, IContext.EMPTYPLAN,
            Stream.of( MATRIX2 ).map( CRawTerm::of ).collect( Collectors.toList() ),
            l_return
        );

        Assertions.assertEquals( 2, l_return.size() );
        Assertions.assertTrue( l_return.get( 0 ).raw() instanceof DoubleMatrix2D );
        Assertions.assertTrue( l_return.get( 1 ).raw() instanceof DoubleMatrix2D );

        Assertions.assertArrayEquals( new double[][]{{1.0, 0.0}, {0.0, 1.0}}, l_return.get( 0 ).<DoubleMatrix2D>raw().toArray() );
        Assertions.assertArrayEquals( new double[][]{{0.0, 1.0}, {1.0, 1.0}}, l_return.get( 1 ).<DoubleMatrix2D>raw().toArray() );
    }

    /**
     * test lambda streaming assignable
     */
    @Test
    public void lambdaassignable()
    {
        Assertions.assertTrue( new CLambdaStreaming().assignable().collect( Collectors.toSet() ).contains( BitMatrix.class ) );
    }

    /**
     * test lambda streaming
     */
    @Test
    public void lambda()
    {
        Assertions.assertArrayEquals(
            Stream.of( 1, 0, 0, 1 ).toArray(),
            new CLambdaStreaming().apply( MATRIX1 ).toArray()
        );
    }

    /**
     * test hamming distance
     */
    @Test
    public void hammingdistanceerror()
    {
        Assertions.assertThrows(
            CExecutionIllegealArgumentException.class,
            () -> new CHammingDistance().execute(
                false, IContext.EMPTYPLAN,
                Collections.emptyList(),
                Collections.emptyList()
            )
        );
    }

    /**
     * test set
     */
    @Test
    public void set()
    {
        final IExecution l_set = new CSet();

        l_set.execute(
            false, IContext.EMPTYPLAN,
            Stream.of( MATRIX2, true, 0, 1, 0, 0 ).map( CRawTerm::of ).collect( Collectors.toList() ),
            Collections.emptyList()
        );

        Assertions.assertTrue( MATRIX2.get( 0, 0 ) );
        Assertions.assertTrue( MATRIX2.get( 0, 1 ) );

        l_set.execute(
            false, IContext.EMPTYPLAN,
            Stream.of( MATRIX2, 0.5, 0, 0, 0, 1 ).map( CRawTerm::of ).collect( Collectors.toList() ),
            Collections.emptyList()
        );

        Assertions.assertFalse( MATRIX2.get( 0, 0 ) );
        Assertions.assertFalse( MATRIX2.get( 0, 1 ) );
    }

}
