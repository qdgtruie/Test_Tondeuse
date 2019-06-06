package com.publicissapient.tondeuse;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Report;
import net.jqwik.api.Reporting;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class WebAppTest {

    @Property(tries=1) @Report(Reporting.GENERATED)
    void AnyArgCanBePassed(@ForAll final String[] args){
        assertDoesNotThrow(()->WebApp.main(args));
    }
}