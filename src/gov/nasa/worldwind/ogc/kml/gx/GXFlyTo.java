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

package gov.nasa.worldwind.ogc.kml.gx;

import gov.nasa.worldwind.util.xml.XMLEventParserContext;
import gov.nasa.worldwind.ogc.kml.*;

import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.XMLStreamException;

/**
 * @author tag
 * @version $Id: GXFlyTo.java 1171 2013-02-11 21:45:02Z dcollins $
 */
public class GXFlyTo extends GXAbstractTourPrimitive
{
    public GXFlyTo(String namespaceURI)
    {
        super(namespaceURI);
    }

    @Override
    protected void doAddEventContent(Object o, XMLEventParserContext ctx, XMLEvent event, Object... args)
        throws XMLStreamException
    {
        if (o instanceof KMLAbstractView)
            this.setView((KMLAbstractView) o);
        else
            super.doAddEventContent(o, ctx, event, args);
    }

    public Double getDuration()
    {
        return (Double) this.getField("duration");
    }

    public String getFlyToMode()
    {
        return (String) this.getField("flyToMode");
    }

    public KMLAbstractView getView()
    {
        return (KMLAbstractView) this.getField("AbstractView");
    }

    protected void setView(KMLAbstractView o)
    {
        this.setField("AbstractView", o);
    }
}
