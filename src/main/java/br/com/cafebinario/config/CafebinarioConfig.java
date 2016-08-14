package br.com.cafebinario.config;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.jasypt.encryption.pbe.PBEStringCleanablePasswordEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.ListenerConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TopicConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.spring.cache.HazelcastCacheManager;

import br.com.cafebinario.entity.DomainAccount;
import br.com.cafebinario.entity.UserAccount;
import br.com.cafebinario.notify.data.EventNotifyData;
import br.com.cafebinario.notify.impl.SenderHtmlEmailNotify;
import br.com.cafebinario.notify.interfaces.Sender;
import br.com.cafebinario.register.datamemory.CafebinarioMemory;
import br.com.cafebinario.register.datamemory.SecureMemoryData;
import br.com.cafebinario.register.listener.DomainAccountEventListener;
import br.com.cafebinario.register.listener.EventNotifyDataEventListener;
import br.com.cafebinario.register.listener.UserAccountEventListener;

@Configuration
@ConfigurationProperties("cafebinario")
@CacheConfig
public class CafebinarioConfig {

	@Value(value = "${secure.systemPassword:ABCDEF0123456789}")
	private String systemPassword;

	private JavaMailSender javaMailSender;

	private UserAccountEventListener userAccountEventListener;

	private DomainAccountEventListener domainAccountEventListener;

	private EventNotifyDataEventListener eventNotifyDataEventListener;

	private CafebinarioMemory cafebinarioMemory;
	
	private HazelcastInstance hazelcastInstance;

	@Bean
	PBEStringCleanablePasswordEncryptor standardPBEStringEncryptor() {
		return new StandardPBEStringEncryptor();
	}

	@Bean
	Sender sender() {
		return new SenderHtmlEmailNotify();
	}

	@Bean
	JavaMailSender javaMailSender() {
		if (javaMailSender == null) {
			this.javaMailSender = new JavaMailSenderImpl();
		}
		return javaMailSender;
	}

	@Bean
	SecretKey cafeBinarioSecretKey() {
		return new SecretKeySpec(systemPassword.getBytes(), "AES");
	}

	@Bean
	ITopic<UserAccount> userAccountTopic() {
		return hazelcastInstance().getTopic("userAccountTopic");
	}

	@Bean
	ITopic<DomainAccount> domainAccountTopic() {
		return hazelcastInstance().getTopic("domainAccountTopic");
	}

	@Bean
	ITopic<EventNotifyData> eventNotifyTopic() {
		return hazelcastInstance().getTopic("eventNotifyTopic");
	}

	@Bean
	EventListener userAccountEventListener() {
		if (userAccountEventListener == null) {
			userAccountEventListener = new UserAccountEventListener();
		}
		return userAccountEventListener;
	}

	@Bean
	EventListener domainAccountEventListener() {
		if (domainAccountEventListener == null) {
			domainAccountEventListener = new DomainAccountEventListener();
		}
		return domainAccountEventListener;
	}

	@Bean
	EventListener eventNotifyDataEventListener() {
		if (eventNotifyDataEventListener == null) {
			eventNotifyDataEventListener = new EventNotifyDataEventListener();
		}
		return eventNotifyDataEventListener;
	}

	@Bean
	CafebinarioMemory cafebinarioMemory() {
		if (cafebinarioMemory == null) {
			this.cafebinarioMemory = CafebinarioMemory.getInstance(secureMap());
		}

		return cafebinarioMemory;
	}
	
	@Bean
    CacheManager cacheManager() {
        return new HazelcastCacheManager(hazelcastInstance());
    }
	
	private HazelcastInstance hazelcastInstance() {
		if(hazelcastInstance == null){
			hazelcastInstance = Hazelcast.getOrCreateHazelcastInstance(hazelCastConfig("cafebinario-0"));
		}
		
		return hazelcastInstance;
	}

	private Map<String, SecureMemoryData> secureMap() {
		return hazelcastInstance().getMap("secureMap");
	}

	private Config hazelCastConfig(String menberName) {
		Config config = new Config(menberName);
		config.setGroupConfig(hazelCastGroupConfig());
		config.setNetworkConfig(hazelCastNetworkConfig());
		config.setTopicConfigs(mapTopicConfigs());
		config.setMapConfigs(mapConfigs());
		return config;
	}

	private Map<String, MapConfig> mapConfigs() {
		Map<String, MapConfig> mapConfig = new HashMap<>();
		mapConfig.put("secureMap", secureMapConfig());
		return mapConfig;
	}

	private MapConfig secureMapConfig() {
		MapConfig mapConfig = new MapConfig("secureMap");
		return mapConfig;
	}

	private Map<String, TopicConfig> mapTopicConfigs() {
		Map<String, TopicConfig> topicConfig = new HashMap<>();
		topicConfig.put("persistenceUserTopic", userAccountTopicConfig());
		topicConfig.put("persistenceDomainTopic", domainAccountTopicConfig());
		topicConfig.put("sendEventNotifyDataTopic", sendEventNotifyDataTopic());
		return topicConfig;
	}

	private TopicConfig userAccountTopicConfig() {
		TopicConfig topicConfig = new TopicConfig("userAccountTopic");
		topicConfig.setGlobalOrderingEnabled(false);
		topicConfig.setMessageListenerConfigs(userTopicListenerConfigs());
		return topicConfig;
	}

	private TopicConfig domainAccountTopicConfig() {
		TopicConfig topicConfig = new TopicConfig("domainAccountTopic");
		topicConfig.setGlobalOrderingEnabled(false);
		topicConfig.setMessageListenerConfigs(domainTopicListenerConfigs());
		return topicConfig;
	}

	private TopicConfig sendEventNotifyDataTopic() {
		TopicConfig topicConfig = new TopicConfig("eventNotifyDataTopic");
		topicConfig.setGlobalOrderingEnabled(false);
		topicConfig.setMessageListenerConfigs(eventNotifyDataTopicListenerConfigs());
		return topicConfig;
	}

	private List<ListenerConfig> userTopicListenerConfigs() {
		List<ListenerConfig> list = new ArrayList<>();
		list.add(userTopicListenerConfig());
		return list;
	}

	private List<ListenerConfig> domainTopicListenerConfigs() {
		List<ListenerConfig> list = new ArrayList<>();
		list.add(domainTopicListenerConfig());
		return list;
	}

	private List<ListenerConfig> eventNotifyDataTopicListenerConfigs() {
		List<ListenerConfig> list = new ArrayList<>();
		list.add(eventNotifyDataTopicListenerConfig());
		return list;
	}

	private ListenerConfig userTopicListenerConfig() {
		ListenerConfig listenerConfig = new ListenerConfig(userAccountEventListener());
		return listenerConfig;
	}

	private ListenerConfig domainTopicListenerConfig() {
		ListenerConfig listenerConfig = new ListenerConfig(domainAccountEventListener());
		return listenerConfig;
	}

	private ListenerConfig eventNotifyDataTopicListenerConfig() {
		ListenerConfig listenerConfig = new ListenerConfig(eventNotifyDataEventListener());
		return listenerConfig;
	}

	private NetworkConfig hazelCastNetworkConfig() {
		NetworkConfig networkConfig = new NetworkConfig();
		networkConfig.setPort(5560);
		networkConfig.setPortAutoIncrement(true);
		networkConfig.setJoin(hazelCastJoin());
		networkConfig.setPortCount(1);
		return networkConfig;
	}

	private JoinConfig hazelCastJoin() {
		JoinConfig joinConfig = new JoinConfig();
		joinConfig.setMulticastConfig(hazelCastMulticastConfig());
		return joinConfig;
	}

	private MulticastConfig hazelCastMulticastConfig() {
		MulticastConfig multicastConfig = new MulticastConfig();
		multicastConfig.setEnabled(true);
		multicastConfig.setLoopbackModeEnabled(true);
		multicastConfig.setMulticastGroup("224.2.2.3");
		multicastConfig.setMulticastPort(4480);
		multicastConfig.setMulticastTimeToLive(255);
		return multicastConfig;
	}

	private GroupConfig hazelCastGroupConfig() {
		GroupConfig groupConfig = new GroupConfig("cafebinario", "cafebinario");
		return groupConfig;
	}
}