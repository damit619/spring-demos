package org.javatigers.security.model;

import java.io.Serializable;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.javatigers.security.model.listeners.AuditEntityListener;

/**
 * AbstractVO default implementation for hascode, equals, clone.
 * 
 * @author Amit Dhiman
 *
 */
@EntityListeners(AuditEntityListener.class)
@MappedSuperclass
public class AbstractVO implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2958443592208189822L;
	protected static final boolean TEST_TRANSIENTS = false;
	/**
     * Serialization based clone. <strong>Might be slow - so take care</strong> {@inheritDoc}
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return SerializationUtils.clone(this);
    }

    /**
     * Reflection based hashCode {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, TEST_TRANSIENTS);
    }

    /**
     * Reflection based toString {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    /**
     * Reflection based equals {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, TEST_TRANSIENTS);
    }

}
