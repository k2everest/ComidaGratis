package br.com.cafebinario.register.datamemory;

import java.io.Serializable;

import javax.crypto.SecretKey;

import org.springframework.data.util.Pair;
import org.springframework.util.Assert;

import br.com.cafebinario.register.vo.user.UserAuthenticationVO;

public final class SecureMemoryData implements Serializable{

	private static final long serialVersionUID = 2619163159194853525L;

	private final Pair<String, SecretKey> pair;
	private final UserAuthenticationVO userAuthenticationVO;
	private final Long createKeyTimestamp;
	
	public SecureMemoryData(final Pair<String, SecretKey> pair, final UserAuthenticationVO userAuthenticationVO,
			final Long createKeyTimestamp) {
		super();
		this.pair = pair;
		this.userAuthenticationVO = userAuthenticationVO;
		this.createKeyTimestamp = createKeyTimestamp;
	}

	public Pair<String, SecretKey> getPair() {
		return pair;
	}

	public UserAuthenticationVO getUserAuthenticationVO() {
		return userAuthenticationVO;
	}

	public Long getCreateKeyTimestamp() {
		return createKeyTimestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createKeyTimestamp == null) ? 0 : createKeyTimestamp.hashCode());
		result = prime * result + ((pair == null) ? 0 : pair.hashCode());
		result = prime * result + ((userAuthenticationVO == null) ? 0 : userAuthenticationVO.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SecureMemoryData other = (SecureMemoryData) obj;
		if (createKeyTimestamp == null) {
			if (other.createKeyTimestamp != null)
				return false;
		} else if (!createKeyTimestamp.equals(other.createKeyTimestamp))
			return false;
		if (pair == null) {
			if (other.pair != null)
				return false;
		} else if (!pair.equals(other.pair))
			return false;
		if (userAuthenticationVO == null) {
			if (other.userAuthenticationVO != null)
				return false;
		} else if (!userAuthenticationVO.equals(other.userAuthenticationVO))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SecureMemoryData [pair=" + pair + ", userAuthenticationVO=" + userAuthenticationVO
				+ ", createKeyTimestamp=" + createKeyTimestamp + "]";
	}

	public void verify() {
		Assert.isTrue(createKeyTimestamp > System.currentTimeMillis() - 20 * 60 * 60 * 1000);		
	}
}
