/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.vector;



import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;



import com.vector.model.cat.CatFact;
import com.vector.model.cat.CatImage;
import com.vector.model.google.Google;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import com.vector.event.handler.TrackScheduler;
import com.vector.model.Dog;
import com.vector.services.APIService;
import com.vector.services.APIServiceImpl;
import com.vector.services.Command;
import com.vector.services.LavaPlayerAudioProvider;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.channel.TypingStartEvent;

import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.presence.ClientActivity;
import discord4j.gateway.intent.Intent;
import discord4j.gateway.intent.IntentSet;
import discord4j.voice.AudioProvider;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static discord4j.core.object.presence.ClientActivity.listening;
public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);
    private static APIService apiService = new APIServiceImpl();
    private static Map<String,String> googleAPIHeaders;
    private static Map<String,Command> commands = new HashMap<>();
    
    static {
        googleAPIHeaders = new HashMap<String,String>();
        googleAPIHeaders.put("x-user-agent", "desktop");
        googleAPIHeaders.put("x-proxy-location", "EU");
        googleAPIHeaders.put("x-rapidapi-key", "cb99568f82msh93a678a90a73912p171224jsn2b883ecdd439");
       
        // commands under construction
        commands.put("ping",event->Mono.just(event.getMessage()).flatMap(Message::getChannel).flatMap(channel->channel.createMessage("pong")));
        commands.put("cat",event->
            Mono.just(event.getMessage().getContent()).map(content->Arrays.asList(content)).doOnNext(command->{
                
                if(command.size() < 2) Mono.just(event.getMessage()).flatMap(Message::getChannel).flatMap(channel->channel.createMessage("Correct Syntax : $cat [option]")); 
                if(command.get(1).equalsIgnoreCase("image")) {
                    Mono.just("Hello");
                }
                Mono.just(event.getMessage()).flatMap(Message::getChannel).flatMap(channel->channel.createMessage("Hello"));
                //event.getMessage().getChannel().flatMap(channel->channel.createMessage(message));
            })
        );
    }
    public static void main(String[] args) {
         
        final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();


        playerManager.getConfiguration()
        .setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);


        AudioSourceManagers.registerRemoteSources(playerManager);


        final AudioPlayer player = playerManager.createPlayer();


        AudioProvider provider = new LavaPlayerAudioProvider(player);
        final TrackScheduler scheduler = new TrackScheduler(player);
        DiscordClient client = DiscordClient.create(args[0]);


        player.addListener(scheduler);
        logger.info("Hello");
        Mono<Void> login = client.withGateway(gateway->
            gateway.on(ReadyEvent.class,event->
                Mono.fromRunnable(()->{
                    final User self = event.getSelf();
                    System.out.println(String.format("Logged in as %s#%s%n",self.getUsername(),self.getDiscriminator()));
                    
                    // Iterator<Intent> set = IntentSet.all().iterator();
                    // while(set.hasNext()){
                    //     System.out.println(set.next());
                    // }
                })
            ).then().and(gateway.on(MessageCreateEvent.class,event -> {
                Message message = event.getMessage();
                if(message.getAuthor().get().isBot()){
                    return Mono.empty();
                }
                String[] messages = message.getContent().split(" ");
                if(messages[0].equalsIgnoreCase("$ping")){
                    return message.getChannel().flatMap(channel->channel.createMessage("pong!"));
                } else if(messages[0].equals("$cat")) {
                    System.out.print("Inside !cat");
                    if(messages.length <2){
                        return message.getChannel().flatMap(channel->channel.createMessage("correct syntax:!cat [option]"));
                    }
                    if(messages[1].equalsIgnoreCase("fact")){
                        System.out.println("Inside catfact");
                        AtomicReference<CatFact> fact = new AtomicReference<>();

                        
                        fact.set(apiService.getAPIResponse(CatFact.class,"https://catfact.ninja/fact"));
                        return message.getChannel().flatMap(channel->channel.createMessage(fact.get().getFact()));
                    }else if(messages[1].equalsIgnoreCase("image")){
                        AtomicReference<List<CatImage>> catImages= new AtomicReference<>();
                        
                        try{
                            catImages.set(apiService.getAPIResponseInList(CatImage.class, "https://api.thecatapi.com/v1/images/search",new HashMap<>()));
                        }catch(Exception exception){
                            exception.printStackTrace();
                        }
                        System.out.println(" image");
                        
                        System.out.println(catImages.get().get(0).getUrl());
                        return message.getChannel().flatMap(channel->channel.createMessage(catImages.get().get(0).getUrl()));
                    }
                    return message.getChannel().flatMap(channel->channel.createMessage("the fuck you mean!!"));
                } else if(messages[0].equalsIgnoreCase("$dog")){
                    if(messages.length < 2) return message.getChannel().flatMap(channel->channel.createMessage("Correct Syntax : !dog [option]"));
                    switch(messages[1].toLowerCase()){
                        case "image":
                        return message.getChannel().flatMap(channel->channel.createMessage(apiService.getAPIResponse(Dog.class, "https://dog.ceo/api/breeds/image/random").getMessage()));
                    }
                    return message.getChannel().flatMap(channel->channel.createMessage("the fuck you mean!!"));
                    
                 }else if(messages[0].equalsIgnoreCase("$google")){ 
                    if(messages.length < 3) return message.getChannel().flatMap(channel->channel.createMessage("!google [option] [option]"));
                    if(messages[1].equalsIgnoreCase("image")){

                        return message.getChannel().flatMap(channel->{
                            System.out.println("i am under google image");
                            Google google = null;
                            try{
                                
                                google = apiService.getAPIResponse(Google.class, String.format("https://google-search3.p.rapidapi.com/api/v1/search/q=%s&num=1",messages[2]), googleAPIHeaders);
                            }catch(Exception exception){
                                exception.printStackTrace();
                                System.err.println("Message : "+ exception.getMessage());
                                System.err.println("Cause : "+ exception.getCause());
                            }
                            assert google !=null;
                            System.out.println(google);
                            return channel.createMessage(google.getResults().toString());
                                          
                        });
                    }
                 }else if(messages[0].equalsIgnoreCase("$join")){
                     
                    return Mono.justOrEmpty(event.getMember().orElse(null))
                    .flatMap(Member::getVoiceState)
                    .flatMap(VoiceState::getChannel)
                    // join returns a VoiceConnection which would be required if we were
                    // adding disconnection features, but for now we are just ignoring it.
                    .flatMap(channel -> channel.join(spec -> spec.setProvider(provider)));
                 }else if(messages[0].equalsIgnoreCase("$play")) {
                    if(messages.length < 2) return message.getChannel().flatMap(channel->channel.createMessage("Correct Syntax : $play [link]"));
                    
                    Mono.justOrEmpty(playerManager.loadItem(messages[1],scheduler)).and(message.getChannel().flatMap(channel->channel.createMessage("")));
                 }
                return Mono.empty();
            }).then().and(gateway.on(TypingStartEvent.class,event->{
                return Mono.fromRunnable(()->{
                    logger.info("Me inside TypingStartEvent");
                    System.out.printf("%s#%s started typing%n",event.getUser().blockOptional().get().getUsername(),event.getUser().blockOptional().get().getDiscriminator());
                }); 
                
            })).then())
        );
        login.block();
    }
}
