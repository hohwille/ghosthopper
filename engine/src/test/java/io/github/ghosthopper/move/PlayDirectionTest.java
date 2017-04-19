/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.ghosthopper.move;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import io.github.ghosthopper.game.PlayGame;

/**
 * Test of {@link PlayDirection}.
 */
public class PlayDirectionTest extends Assertions {

  /**
   * Test of {@link PlayDirection#getCombinations()}.
   */
  @Test
  public void testCombinations() {

    assertThat(PlayDirection.EAST.getCombinations()).isEmpty();
    assertThat(PlayDirection.SOUTH_EAST.getInverse()).isSameAs(PlayDirection.NORTH_WEST);
    assertThat(PlayDirection.SOUTH.getInverse()).isSameAs(PlayDirection.NORTH);
    assertThat(PlayDirection.SOUTH_WEST.getInverse()).isSameAs(PlayDirection.NORTH_EAST);
    assertThat(PlayDirection.WEST.getInverse()).isSameAs(PlayDirection.EAST);
    assertThat(PlayDirection.NORTH_WEST.getInverse()).isSameAs(PlayDirection.SOUTH_EAST);
    assertThat(PlayDirection.NORTH.getInverse()).isSameAs(PlayDirection.SOUTH);
    assertThat(PlayDirection.NORTH_EAST.getInverse()).isSameAs(PlayDirection.SOUTH_WEST);
  }

  /**
   * Test of {@link PlayDirection#getInverse()}.
   */
  @Test
  public void testInverse() {

    assertThat(PlayDirection.EAST.getInverse()).isSameAs(PlayDirection.WEST);
    assertThat(PlayDirection.SOUTH_EAST.getInverse()).isSameAs(PlayDirection.NORTH_WEST);
    assertThat(PlayDirection.SOUTH.getInverse()).isSameAs(PlayDirection.NORTH);
    assertThat(PlayDirection.SOUTH_WEST.getInverse()).isSameAs(PlayDirection.NORTH_EAST);
    assertThat(PlayDirection.WEST.getInverse()).isSameAs(PlayDirection.EAST);
    assertThat(PlayDirection.NORTH_WEST.getInverse()).isSameAs(PlayDirection.SOUTH_EAST);
    assertThat(PlayDirection.NORTH.getInverse()).isSameAs(PlayDirection.SOUTH);
    assertThat(PlayDirection.NORTH_EAST.getInverse()).isSameAs(PlayDirection.SOUTH_WEST);
  }

  /**
   * Test of {@link PlayDirection#turn(boolean)} with 90 degrees per step (default).
   */
  @Test
  public void testTurn90() {

    PlayGame.getCurrentGame().end();
    // clockwise
    assertThat(PlayDirection.EAST.turn(true)).isSameAs(PlayDirection.SOUTH);
    assertThat(PlayDirection.SOUTH.turn(true)).isSameAs(PlayDirection.WEST);
    assertThat(PlayDirection.WEST.turn(true)).isSameAs(PlayDirection.NORTH);
    assertThat(PlayDirection.NORTH.turn(true)).isSameAs(PlayDirection.EAST);

    // counter-clockwise
    assertThat(PlayDirection.EAST.turn(false)).isSameAs(PlayDirection.NORTH);
    assertThat(PlayDirection.NORTH.turn(false)).isSameAs(PlayDirection.WEST);
    assertThat(PlayDirection.WEST.turn(false)).isSameAs(PlayDirection.SOUTH);
    assertThat(PlayDirection.SOUTH.turn(false)).isSameAs(PlayDirection.EAST);
  }

  /**
   * Test of {@link PlayDirection#turn(boolean)} with 45 degrees per step (custom game).
   */
  @Test
  public void testTurn45() {

    PlayGame game = new PlayGame("45") {

      @Override
      protected boolean isSupportingDiagonalDirections() {

        return true;
      }
    };
    game.start();

    // clockwise
    assertThat(PlayDirection.EAST.turn(true)).isSameAs(PlayDirection.SOUTH_EAST);
    assertThat(PlayDirection.SOUTH_EAST.turn(true)).isSameAs(PlayDirection.SOUTH);
    assertThat(PlayDirection.SOUTH.turn(true)).isSameAs(PlayDirection.SOUTH_WEST);
    assertThat(PlayDirection.SOUTH_WEST.turn(true)).isSameAs(PlayDirection.WEST);
    assertThat(PlayDirection.WEST.turn(true)).isSameAs(PlayDirection.NORTH_WEST);
    assertThat(PlayDirection.NORTH_WEST.turn(true)).isSameAs(PlayDirection.NORTH);
    assertThat(PlayDirection.NORTH.turn(true)).isSameAs(PlayDirection.NORTH_EAST);
    assertThat(PlayDirection.NORTH_EAST.turn(true)).isSameAs(PlayDirection.EAST);

    // counter-clockwise
    assertThat(PlayDirection.EAST.turn(false)).isSameAs(PlayDirection.NORTH_EAST);
    assertThat(PlayDirection.NORTH_EAST.turn(false)).isSameAs(PlayDirection.NORTH);
    assertThat(PlayDirection.NORTH.turn(false)).isSameAs(PlayDirection.NORTH_WEST);
    assertThat(PlayDirection.NORTH_WEST.turn(false)).isSameAs(PlayDirection.WEST);
    assertThat(PlayDirection.WEST.turn(false)).isSameAs(PlayDirection.SOUTH_WEST);
    assertThat(PlayDirection.SOUTH_WEST.turn(false)).isSameAs(PlayDirection.SOUTH);
    assertThat(PlayDirection.SOUTH.turn(false)).isSameAs(PlayDirection.SOUTH_EAST);
    assertThat(PlayDirection.SOUTH_EAST.turn(false)).isSameAs(PlayDirection.EAST);

    game.end();
  }

  /**
   * Test of {@link PlayDirection#turn(boolean)} with 180 degrees per step (custom game).
   */
  @Test
  public void testTurn180() {

    PlayGame game = new PlayGame("180") {

      @Override
      protected Set<PlayDirection> createDirections() {

        Set<PlayDirection> set = new HashSet<>();
        set.add(PlayDirection.WEST);
        set.add(PlayDirection.EAST);
        return set;
      }
    };
    game.start();

    // clockwise
    assertThat(PlayDirection.EAST.turn(true)).isSameAs(PlayDirection.WEST);
    assertThat(PlayDirection.WEST.turn(true)).isSameAs(PlayDirection.EAST);

    // counter-clockwise
    assertThat(PlayDirection.EAST.turn(false)).isSameAs(PlayDirection.WEST);
    assertThat(PlayDirection.WEST.turn(false)).isSameAs(PlayDirection.EAST);

    game.end();
  }

  /**
   * Test of {@link PlayDirection#getRotationZ()}.
   */
  @Test
  public void testGetRotationZ() {

    assertThat(PlayDirection.NORTH.getRotationZ()).isEqualTo(0);
    assertThat(PlayDirection.EAST.getRotationZ()).isEqualTo(90);
    assertThat(PlayDirection.SOUTH.getRotationZ()).isEqualTo(180);
    assertThat(PlayDirection.WEST.getRotationZ()).isEqualTo(270);
    assertThat(PlayDirection.NORTH_EAST.getRotationZ()).isEqualTo(45);
    assertThat(PlayDirection.SOUTH_EAST.getRotationZ()).isEqualTo(135);
    assertThat(PlayDirection.SOUTH_WEST.getRotationZ()).isEqualTo(225);
    assertThat(PlayDirection.NORTH_WEST.getRotationZ()).isEqualTo(315);
    PlayDirection chessKnightMove = new PlayDirection(PlayDirection.NORTH, PlayDirection.NORTH, PlayDirection.EAST);
    assertThat(chessKnightMove.getRotationZ()).isEqualTo(30);
  }

}
