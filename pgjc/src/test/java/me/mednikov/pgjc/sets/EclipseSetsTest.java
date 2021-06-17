package me.mednikov.pgjc.sets;

import java.util.Set;
import org.assertj.core.api.Assertions;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;
import org.testng.annotations.Test;

class EclipseSetsTest {
    
    @Test
    void unionTest(){
        ImmutableSet<Integer> set1 = Sets.immutable.of(1, 2, 3,4,5);
        ImmutableSet<Integer> set2 = Sets.immutable.of(6,7,8,9,10);
        ImmutableSet<Integer> union = set1.union(set2);
        Set<Integer> result = union.castToSet();
        Assertions.assertThat(result).hasSize(10);
    }
    
    @Test
    void differenceTest(){
        ImmutableSet<Integer> set1 = Sets.immutable.of(1,2,3,4,5,6,7,8,9,10);
        ImmutableSet<Integer> set2 = Sets.immutable.of(2,4,6,8,10);
        ImmutableSet<Integer> difference = set1.difference(set2);
        Set<Integer> result = difference.castToSet();
        Assertions.assertThat(result).contains(1,3,5,7,9);
    }
    
    @Test
    void intersectionTest(){
        ImmutableSet<Integer> set1 = Sets.immutable.of(1,2,3,4,5,6,7,8,9,10);
        ImmutableSet<Integer> set2 = Sets.immutable.of(2,4,6,8,10);
        ImmutableSet<Integer> intersection = set1.intersect(set2);
        Set<Integer> result = intersection.castToSet();
        Assertions.assertThat(result).contains(2,4,6,8,10);
    }
    
    @Test
    void disjointTest(){
        ImmutableSet<Integer> set1 = Sets.immutable.of(1,2,3,4,5);
        ImmutableSet<Integer> set2 = Sets.immutable.of(6,7,8,9,10);
        boolean isDisjoint = !set1.containsAllIterable(set2);
        Assertions.assertThat(isDisjoint).isTrue();
    }
}
