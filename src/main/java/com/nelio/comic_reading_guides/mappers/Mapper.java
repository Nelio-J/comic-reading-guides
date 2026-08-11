package com.nelio.comic_reading_guides.mappers;

public interface Mapper<A,B> {

    B mapTo(A a);

    A mapFrom(B b);
}
