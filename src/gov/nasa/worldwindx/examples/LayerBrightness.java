/*
 * Copyright 2006-2009, 2017, 2020 United States Government, as represented by the
 * Administrator of the National Aeronautics and Space Administration.
 * All rights reserved.
 * 
 * The NASA World Wind Java (WWJ) platform is licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * NASA World Wind Java (WWJ) also contains the following 3rd party Open Source
 * software:
 * 
 *     Jackson Parser – Licensed under Apache 2.0
 *     GDAL – Licensed under MIT
 *     JOGL – Licensed under  Berkeley Software Distribution (BSD)
 *     Gluegen – Licensed under Berkeley Software Distribution (BSD)
 * 
 * A complete listing of 3rd Party software notices and licenses included in
 * NASA World Wind Java (WWJ)  can be found in the WorldWindJava-v2.2 3rd-party
 * notices and licenses PDF found in code directory.
 */
package gov.nasa.worldwindx.examples;

import com.jogamp.opengl.util.*;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.event.*;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.layers.*;

import com.jogamp.opengl.GLAnimatorControl;
        
/**
 * Shows how to change brightness of an imagery layer
 * (i.e. a {@link gov.nasa.worldwind.layers.TiledImageLayer}) *
 */
public class LayerBrightness extends ApplicationTemplate
{
    public static class AppFrame extends ApplicationTemplate.AppFrame implements RenderingListener
    {
        protected GLAnimatorControl animator;
        protected double decreaseOfOpacityPerTick = 0.2;
        protected long lastTime;
        protected double blueMarbleLayerOpacity = getWwd().getModel().getLayers().getLayerByName("NASA Blue Marble Image").getOpacity();
        protected Position eyePosition = Position.fromDegrees(0, 0, 20000000);

        public AppFrame()
        {
            // Reduce the frequency at which terrain is regenerated.
            getWwd().getModel().getGlobe().getTessellator().setUpdateFrequency(5000);
            
            getWwd().getModel().getLayers().getLayerByName("Blue Marble May 2004").setOpacity(1.0d);
            System.out.print(getWwd().getModel().getLayers().getLayerByName("Blue Marble May 2004").getBrightness());
            
            getWwd().getModel().getLayers().getLayerByName("Blue Marble May 2004").setBrightness(0.1f);

            // Add a rendering listener to update a layer's brightness each frame. It's implementation is the
            // stageChanged method below.
            getWwd().addRenderingListener(this);

            // Use a JOGL Animator to spin the globe
            lastTime = System.currentTimeMillis();
            animator = new FPSAnimator((WorldWindowGLCanvas) getWwd(), 60 /*frames per second*/);
            animator.start();
        }

        @Override
        public void stageChanged(RenderingEvent event)
        {
            if (event.getStage().equals(RenderingEvent.BEFORE_RENDERING))
            {
                // The globe may not be instantiated the first time the listener is called.
                if (getWwd().getView().getGlobe() == null)
                    return;

                long now = System.currentTimeMillis();
                double elapsedSeconds = (now - lastTime) * 1.0e-3;
                //double rotationDegrees = rotationDegreesPerSecond * elapsedSeconds;
                double currentOpacity = decreaseOfOpacityPerTick * elapsedSeconds;
                //System.out.print(opacity + "\n");
                //System.out.print(elapsedSeconds + "\n");
                lastTime = now;

//                double lat = eyePosition.getLatitude().degrees;
//                double lon = Angle.normalizedDegreesLongitude(eyePosition.getLongitude().degrees + rotationDegrees);
//                double alt = eyePosition.getAltitude();

                //eyePosition = Position.fromDegrees(lat, lon, alt);
                //getWwd().getModel().getLayers().getLayerByName("Blue Marble May 2004").setOpacity(currentOpacity);
                //getWwd().getView().stopAnimations();
                
                
                
                //getWwd().getView().setEyePosition(eyePosition);
            }
        }
    }

    public static void main(String[] args)
    {
        ApplicationTemplate.start("WorldWind Layer Brightness", AppFrame.class);
    }
}
