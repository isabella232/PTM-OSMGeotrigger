//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.16 at 03:08:32 PM CET 
//


package de.esri.osm.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}overpass"/>
 *         &lt;element ref="{}arcgis"/>
 *         &lt;element ref="{}matching"/>
 *         &lt;element ref="{}trigger"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "overpass",
    "arcgis",
    "matching",
    "trigger"
})
@XmlRootElement(name = "query")
public class Query {

    @XmlElement(required = true)
    protected Overpass overpass;
    @XmlElement(required = true)
    protected Arcgis arcgis;
    @XmlElement(required = true)
    protected Matching matching;
    @XmlElement(required = true)
    protected Trigger trigger;

    /**
     * Gets the value of the overpass property.
     * 
     * @return
     *     possible object is
     *     {@link Overpass }
     *     
     */
    public Overpass getOverpass() {
        return overpass;
    }

    /**
     * Sets the value of the overpass property.
     * 
     * @param value
     *     allowed object is
     *     {@link Overpass }
     *     
     */
    public void setOverpass(Overpass value) {
        this.overpass = value;
    }

    /**
     * Gets the value of the arcgis property.
     * 
     * @return
     *     possible object is
     *     {@link Arcgis }
     *     
     */
    public Arcgis getArcgis() {
        return arcgis;
    }

    /**
     * Sets the value of the arcgis property.
     * 
     * @param value
     *     allowed object is
     *     {@link Arcgis }
     *     
     */
    public void setArcgis(Arcgis value) {
        this.arcgis = value;
    }

    /**
     * Gets the value of the matching property.
     * 
     * @return
     *     possible object is
     *     {@link Matching }
     *     
     */
    public Matching getMatching() {
        return matching;
    }

    /**
     * Sets the value of the matching property.
     * 
     * @param value
     *     allowed object is
     *     {@link Matching }
     *     
     */
    public void setMatching(Matching value) {
        this.matching = value;
    }

    /**
     * Gets the value of the trigger property.
     * 
     * @return
     *     possible object is
     *     {@link Trigger }
     *     
     */
    public Trigger getTrigger() {
        return trigger;
    }

    /**
     * Sets the value of the trigger property.
     * 
     * @param value
     *     allowed object is
     *     {@link Trigger }
     *     
     */
    public void setTrigger(Trigger value) {
        this.trigger = value;
    }

}
