/*
 * @(#)StarRating.java	28/05/2010
 *
 * Copyright 2010 Spyros Soldatos
 */
package com.googlecode.starrating;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.event.CellEditorListener;

/**
 * StarRating Class<br />
 * StarRating is a JPanel that holds images of half stars<br />
 * The rate ranges from 0 to the number of stars with a step of 0.5.<br />
 * The rate is shown in the {@link ValueLabel} which can be visible or not<br />
 * A {@link RemoveButton} (which can be disabled too) sets the rate to 0.0 <br />
 * When the rate changes a property change event is fired.The properties name
 * is {@link #RATE_CHANGED}.<br />
 * A {@link PropertyChangeListener} can be attached to the {@link StarRating} to
 * perform additional tasks (eg save rate to a database etc)<br />
 * When {@link StarRating} is used as a cell editor a {@link CellEditorListener}
 * can be attached to the editor  and listen for {@link CellEditorListener#editingStopped}
 * to get the {@link StarRating} new value.
 * @author ssoldatos
 * @since version 0.9
 */
public class StarRating extends javax.swing.JPanel implements StarRatingConstants {

  /** The rate */
  private double rate;
  /** The maximum rate*/
  private int maxRate;
  /** An ArrayList holding the StarRating stars */
  private ArrayList<Star> stars;
  /** The value label */
  private ValueLabel valueLabel;
  /** The remove button */
  private RemoveButton removeButton;
  /** If {@link #valueLabel} is shown */
  private boolean valueLabelVisible = false;
  /** If {@link #removeButton} is shown */
  private boolean removeButtonVisible = false;
  /** The url of the star image to use **/
  private URL urlStarImage = getClass().getResource(STAR_IMAGE);
  /** If the {@link StarRating} is in edit mode **/
  boolean isEditing = true;
  

  /**
   * Creates a default StarRating with a {@link #rate} of 0.0 a
   * {@link #maxRate} of 5 and with the default {@link #starImage}
   */
  public StarRating() {
    this(0.0, 5, null);
  }

  /**
   * Creates a default {@link StarRating} with a initial rate
   * @param rate The rate
   */
  public StarRating(double rate) {
    this(rate, 5, null);
  }

  /**
   * Creates a default {@link StarRating} with an initial rate and a maximun rate
   * @param rate The initial rate
   * @param maxRate The maximum rate
   */
  public StarRating(double rate, int maxRate) {
    this(rate, maxRate, null);
  }

  /** Creates a StarRating with the initial rate and a maximum rate<br />
   * The {@link #removeButton} is shown by default while the {@link #valueLabel}
   * is hidden.
   * @param rate The initial rate
   * @param maxRate The maximum rate
   * @param starImage The path to star image to use or an image of the {@link StarRatingConstants}
   *
   */
  public StarRating(double rate, int maxRate, URL starImage) {
    super();
    this.maxRate = maxRate;
      if (starImage == null) {
        setStarImage(null);
      } else {
        setStarImage(starImage);
      }
    initComponents();
    stars = new ArrayList<Star>();
    valueLabel = new ValueLabel(rate);
    valueLabel.setBackground(getBackground());
    removeButton = new RemoveButton();
    removeButton.setBackground(getBackground());
    for (int i = 0; i < getMaxRate() * 2; i++) {
      boolean enabled;
      enabled = i <= rate * 2 ? true : false;
      stars.add(i, new Star(i, enabled, getUrlStarImage()));
      add(stars.get(i));
      stars.get(i).addStarMouseAdapter();
      stars.get(i).setBackground(getBackground());
    }
    setRate(rate);
    showRemoveButton();
    setPreferredSize(new Dimension(maxRate * 20 + 40, 20));
    setOpaque(false);
    addMouseListener(new StarMouseAdapter(this));
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    setBackground(new java.awt.Color(255, 255, 255));
    setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
    setMaximumSize(new java.awt.Dimension(10000, 20));
    setMinimumSize(new java.awt.Dimension(150, 20));
    setName("StarRating"); // NOI18N
    setOpaque(false);
    setPreferredSize(new java.awt.Dimension(300, 20));
    setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));
  }// </editor-fold>//GEN-END:initComponents

  /**
   * Sets if StarRating is enabled
   * @param enabled If rating is enabled
   */
  public void setRatingEnabled(boolean enabled) {
    setEnabled(enabled);
  }

  /**
   * Gets if StarRating is enabled
   * @return If StarRating is enabled
   */
  public boolean isRatingEnabled() {
    return isEnabled();
  }

  /**
   * Shows the value label next to the stars validates and repaints the StarRating
   */
  private void showValueLabel() {
    if (!isValueLabelVisible()) {
      add(getValueLabel(), getComponentCount());
      valueLabel.addMouseListener(new StarMouseAdapter(valueLabel));
      valueLabelVisible = true;
      validate();
      repaint();
    }
  }

  /**
   * Hides the value label validates and repaints the StarRating
   */
  private void hideValueLabel() {
    if (isValueLabelVisible()) {
      remove(getValueLabel());
      valueLabel.removeMouseListener(new StarMouseAdapter(valueLabel));
      valueLabelVisible = false;
      validate();
      repaint();
    }
  }

  /**
   * Clears the rate (disables stars images) without setting the rate
   */
  void clearRate() {
    for (int i = 0; i < stars.size(); i++) {
      Star star = stars.get(i);
      star.disableStar();
    }
  }

  /**
   * Previews a new rate without setting it
   * @param rate The new rate
   */
  void previewRate(double rate) {
    for (int i = 0; i < stars.size(); i++) {
      Star star = stars.get(i);
      if (i < rate * 2) {
        star.enableStar();
      } else {
        star.disableStar();
      }
    }
    valueLabel.setValue(rate);
  }

  /**
   * Gets the rate
   * @return the rate
   */
  public double getRate() {
    return rate;
  }

  /**
   * Sets the rate and fires a property change to notify listeners
   * @param rate the rate to set
   */
  public void setRate(double rate) {
    double oldRate = this.rate;
    this.rate = rate;
    previewRate(rate);
    valueLabel.setValue(rate);
    firePropertyChange(RATE_CHANGED, oldRate, rate);
  }

  /**
   * Gets the value label
   * @return the valueLabel
   */
  public JLabel getValueLabel() {
    return valueLabel;
  }

  /**
   * Gets if value label is shown
   * @return the isValueLabelShown
   */
  public boolean isValueLabelVisible() {
    return valueLabelVisible;
  }

  /**
   * Gets the remove button
   * @return The remove button
   */
  public RemoveButton getRemoveButton() {
    return removeButton;
  }

  /**
   * Set if remove button is shown
   * @param remove If remove button is shown
   */
  public void setRemoveButtonVisible(boolean remove) {
    if (remove) {
      showRemoveButton();
    } else {
      hideRemoveButton();
    }
    removeButtonVisible = remove;
  }

  /**
   * Gets if remove button is shown
   * @return If remove button is shown
   */
  public boolean isRemoveButtonVisible() {
    return removeButtonVisible;
  }

  /**
   * Sets if the value label is shown
   * @param shown If the value label is shown
   */
  public void setValueLabelVisible(boolean shown) {
    if (shown) {
      showValueLabel();
    } else {
      hideValueLabel();
    }
    valueLabelVisible = shown;
  }

  /**
   * Hide the removeButton validates and repaints the StarRating
   */
  private void hideRemoveButton() {
    remove(removeButton);
    removeButtonVisible = false;
    removeButton.removeMouseListener(new StarMouseAdapter(removeButton));
    validate();
    repaint();
  }

  /**
   * Shows the removeButton validates and repaints the StarRating
   */
  private void showRemoveButton() {
    if (!isRemoveButtonVisible()) {
      removeButton.setOpaque(false);
      add(removeButton, 0);
      removeButton.addMouseListener(new StarMouseAdapter(removeButton));
      removeButtonVisible = true;
      validate();
      repaint();
    }
  }

  /**
   * @return the maxRate
   */
  public int getMaxRate() {
    return maxRate;
  }

  /**
   * Changes the maximum rate
   * @param maxRate the maxRate to set
   */
  public void setMaxRate(int maxRate) {
    if (maxRate > getMaxRate()) {
      for (int i = stars.size(); i < maxRate * 2; i++) {
        stars.add(i, new Star(i, false, getUrlStarImage()));
        add(stars.get(i), i + 1);
        stars.get(i).addStarMouseAdapter();
      }
    } else {
      for (int i = stars.size(); i > maxRate * 2; i--) {
        remove(stars.get(i - 1));
        stars.remove(i - 1);
      }
      if (getRate() > maxRate) {
        setRate(maxRate);
      }
    }
    setPreferredSize(new Dimension(maxRate * 20 + 40, getHeight()));
    validate();
    repaint();
    this.maxRate = maxRate;
  }

  private void setStarImage(URL resource) {
    if (resource == null) {
      this.urlStarImage = getClass().getResource(STAR_IMAGE);
    } else {
      try {
        BufferedImage starBuffImage = ImageIO.read(resource);
        this.urlStarImage = resource;
      } catch (IOException ex) {
        this.urlStarImage = getClass().getResource(STAR_IMAGE);
      }
    }
  }

  public void changeStarImage(URL resource) {
    setStarImage(resource);
    changeStarImages();
    validate();
    repaint();
  }

  private void changeStarImages() {
    for (int i = 0; i < stars.size(); i++) {
      Star star = stars.get(i);
      star.setStarImage(getUrlStarImage());
    }
  }

  public static BufferedImage resizeImage(BufferedImage img, int imageHeight) {
    int w = img.getWidth();
    int h = img.getHeight();
    BufferedImage dimg = dimg = new BufferedImage(imageHeight / (w / h), imageHeight, img.getType());
    Graphics2D g = dimg.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g.drawImage(img, 0, 0, imageHeight / (w / h), imageHeight, 0, 0, w, h, null);
    g.dispose();
    return dimg;
  }

  /**
   * @return the urlStarImage
   */
  private URL getUrlStarImage() {
    return urlStarImage;
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables
}
