/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.game.engine.border;

import net.sf.mmm.game.engine.asset.GameAsset;
import net.sf.mmm.game.engine.type.GameTypeAccess;

/**
 * A {@link GameBorderType} that wraps a {@link #getDelegate() delegate border type} that is hidden until the
 * {@link GameBorder} is {@link GameBorder#pass(GameAsset) passed} for the first time.
 */
public class GameBorderTypeHidden extends GameBorderType {

  private final GameBorderType delegate;

  private boolean hidden;

  /**
   * The constructor.
   *
   * @param delegate - see {@link #getDelegate()}.
   */
  private GameBorderTypeHidden(GameBorderType delegate) {
    super("Hidden");
    this.delegate = delegate;
    this.hidden = true;
  }

  @Override
  public String getId() {

    if (this.hidden) {
      return super.getId();
    }
    return this.delegate.getId();
  }

  /**
   * @return the wrapped {@link GameBorderType}.
   */
  public GameBorderType getDelegate() {

    return this.delegate;
  }

  /**
   * @return {@code true} if this border is hidden, {@code false} otherwise.
   */
  public boolean isHidden() {

    return this.hidden;
  }

  @Override
  public GameTypeAccess getOverlay() {

    if (this.hidden) {
      return super.getOverlay();
    }
    return this.delegate.getOverlay();
  }

  @Override
  public boolean isPassable(GameAsset<?> asset, boolean move, GameBorder border) {

    if (this.hidden) {
      this.hidden = false;
      getGame().sendEvent(border);
    }
    return this.delegate.isPassable(asset, move, border);
  }

  /**
   * @param type the {@link GameBorderType} to hide.
   * @return an instance of this border type.
   */
  public static final GameBorderTypeHidden get(GameBorderType type) {

    return new GameBorderTypeHidden(type);
  }

}
