/*
 * @(#)RemoveButton.java	28/05/2010
 *
 * Copyright 2010 Spyros Soldatos
 */
package com.googlecode.starrating;

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * A label that acts like a buttonn for clearing the rate (sets it to 0.0)
 * The label has a default size of 20x20 with an empty border<br />
 * A {@link StarMouseAdapter} is added for receiving mouse events
 * @author ssoldatos
 * @since version 0.9
 */
public class RemoveButton extends JLabel {

  /** The label's icon enabled image */
  public static String REMOVE_IMAGE = "images/remove.png";
  /** The label's icon disabled image */
  public static String REMOVE_IMAGE_DISABLED = "images/remove_d.png";

  /**
   * Creates a RemoveButton <br />
   */
  public RemoveButton() {
    super();
    setBorder(BorderFactory.createEmptyBorder());
    setPreferredSize(new Dimension(20, 20));
    setIcon(new ImageIcon(getClass().getResource(REMOVE_IMAGE_DISABLED)));
    setCursor(new Cursor(Cursor.HAND_CURSOR));
    addMouseListener(new StarMouseAdapter(-1));
    setOpaque(false);
  }

  
}