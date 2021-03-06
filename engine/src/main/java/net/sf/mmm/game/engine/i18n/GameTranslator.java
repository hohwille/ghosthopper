/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.game.engine.i18n;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import net.sf.mmm.game.engine.Game;
import net.sf.mmm.game.engine.GameNone;
import net.sf.mmm.game.engine.object.GameStateObjectWithId;

/**
 * Localizes text messages to the current locale.
 */
public class GameTranslator {

  private static final Logger LOG = Logger.getLogger(GameTranslator.class.getName());

  private static final String BUNDLE_PREFIX = "game.i18n.";

  private static final String BUNDLE_ROOT = BUNDLE_PREFIX + "Root";

  private final Game game;

  private final Locale creationLocale;

  private final ResourceBundle rootBundle;

  private final ResourceBundle gameBundle;

  /**
   * The constructor.
   *
   * @param game the current {@link Game}.
   */
  public GameTranslator(Game game) {
    this(game, Locale.getDefault());
  }

  /**
   * The constructor.
   *
   * @param game the current {@link Game}.
   * @param locale the {@link Locale}.
   */
  public GameTranslator(Game game, Locale locale) {
    super();
    this.game = game;
    this.creationLocale = locale;
    this.rootBundle = ResourceBundle.getBundle(BUNDLE_ROOT, locale);
    this.gameBundle = getGameBundle(game, locale);
  }

  private static ResourceBundle getGameBundle(Game game, Locale locale) {

    if (game != null) {
      String id = game.getId();
      if ((id != null) && !id.equals(GameNone.ID)) {
        String baseName = BUNDLE_PREFIX + game.getId();
        try {
          return ResourceBundle.getBundle(baseName, locale);
        } catch (Exception e) {
          LOG.warning(e.getMessage());
        }
      }
    }
    return null;
  }

  /**
   * @param key the key of the text message to get. Typically {@link GameStateObjectWithId#getId()}.
   * @return the text message translated to the {@link Locale#getDefault() default locale}.
   */
  public String translate(String key) {

    return translate(key, Locale.getDefault());
  }

  /**
   * @param key the key of the text message to get. Typically {@link GameStateObjectWithId#getId()}.
   * @param locale the {@link Locale} to translate to.
   * @return the text message translated to the given {@link Locale}.
   */
  public String translate(String key, Locale locale) {

    String text = translateWord(key, locale);
    if (text == null) {
      int length = key.length();
      StringBuilder buffer = new StringBuilder(length + 4);
      int start = 0;
      for (int i = 1; i < length; i++) {
        char c;
        if ((i + 1) == length) {
          i++;
          c = '\0';
        } else {
          c = key.charAt(i);
        }
        if (!Character.isAlphabetic(c)) {
          if (i > start) {
            String word = key.substring(start, i);
            String translation = translateWord(word, locale);
            if (translation == null) {
              translation = word;
            }
            buffer.append(translation);
            start = i + 1;
          }
          if (c != '\0') {
            buffer.append(c);
          }
        }
      }
      text = buffer.toString();
    }
    return text;
  }

  private String translateWord(String key, Locale locale) {

    String text = null;
    if (locale == this.creationLocale) {
      text = translate(this.gameBundle, key);
      if (text == null) {
        text = translate(this.rootBundle, key);
      }
    } else {
      ResourceBundle bundle = getGameBundle(this.game, locale);
      text = translate(bundle, key);
      if (text == null) {
        bundle = ResourceBundle.getBundle(BUNDLE_ROOT, locale);
        text = translate(bundle, key);
      }
    }
    return text;
  }

  private String translate(ResourceBundle bundle, String key) {

    if (bundle == null) {
      return null;
    }
    try {
      return bundle.getString(key);
    } catch (MissingResourceException e) {
      // ignore
      return null;
    }
  }

}
