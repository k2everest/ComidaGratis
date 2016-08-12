package br.com.cafebinario.config;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.jasypt.encryption.pbe.PBEStringCleanablePasswordEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.subethamail.wiser.Wiser;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.ListenerConfig;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TopicConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;

import br.com.cafebinario.entiry.UserAccount;
import br.com.cafebinario.notify.data.EventNotifyData;
import br.com.cafebinario.notify.impl.SenderHtmlEmailNotify;
import br.com.cafebinario.notify.interfaces.Sender;
import br.com.cafebinario.register.listener.DomainAccountEventListener;
import br.com.cafebinario.register.listener.EventNotifyDataEventListener;
import br.com.cafebinario.register.listener.UserAccountEventListener;

@Configuration
@ConfigurationProperties("cafebinario")
public class CafebinarioConfig {

	@Value(value = "${secure.systemPassword:ABCDEF0123456789}")
	private String systemPassword;

	private JavaMailSender javaMailSender;

	private static Wiser wiser;

	private UserAccountEventListener userAccountEventListener;

	private DomainAccountEventListener domainAccountEventListener;

	private EventNotifyDataEventListener eventNotifyDataEventListener;

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
	Wiser wiser() {
		if (wiser == null) {
			wiser = new Wiser();
			wiser.setHostname("cafebinario.com.br");
			wiser.setPort(25);
			wiser.start();
		}
		return wiser;
	}

	@Bean
	HazelcastInstance hazelcastInstance() {
		return Hazelcast.getOrCreateHazelcastInstance(hazelCastConfig());
	}

	@Bean
	ITopic<UserAccount> userAccountTopic() {
		return hazelcastInstance().getTopic("userAccountTopic");
	}

	@Bean
	ITopic<UserAccount> domainAccountTopic() {
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
		return userAccountEventListener();
	}

	@Bean
	EventListener domainAccountEventListener() {
		if (domainAccountEventListener == null) {
			domainAccountEventListener = new DomainAccountEventListener();
		}
		return domainAccountEventListener();
	}

	@Bean
	EventListener eventNotifyDataEventListener() {
		if (eventNotifyDataEventListener == null) {
			eventNotifyDataEventListener = new EventNotifyDataEventListener();
		}
		return eventNotifyDataEventListener();
	}

	private Config hazelCastConfig() {
		Config config = new Config("cafebinario");
		config.setGroupConfig(hazelCastGroupConfig());
		config.setNetworkConfig(hazelCastNetworkConfig());
		config.setTopicConfigs(mapTopicConfigs());
		return config;
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
		ListenerConfig listenerConfig = new ListenerConfig(userEventListener());
		return listenerConfig;
	}

	private ListenerConfig domainTopicListenerConfig() {
		ListenerConfig listenerConfig = new ListenerConfig(domainEventListener());
		return listenerConfig;
	}

	private ListenerConfig eventNotifyDataTopicListenerConfig() {
		ListenerConfig listenerConfig = new ListenerConfig(eventNotyfyDataEventListener());
		return listenerConfig;
	}

	private EventListener userEventListener() {

		if (userAccountEventListener == null) {
			userAccountEventListener = new UserAccountEventListener();
		}

		return userAccountEventListener;
	}

	private EventListener domainEventListener() {

		if (domainAccountEventListener == null) {
			domainAccountEventListener = new DomainAccountEventListener();
		}

		return domainAccountEventListener;
	}

	private EventListener eventNotyfyDataEventListener() {

		if (eventNotifyDataEventListener == null) {
			eventNotifyDataEventListener = new EventNotifyDataEventListener();
		}

		return eventNotifyDataEventListener;
	}

	private NetworkConfig hazelCastNetworkConfig() {
		NetworkConfig networkConfig = new NetworkConfig();
		networkConfig.setPort(5560);
		networkConfig.setPortAutoIncrement(true);
		networkConfig.setJoin(hazelCastJoin());
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

	@PreDestroy
	public void shutdown() {
		wiser.stop();
		hazelcastInstance().shutdown();
	}
}