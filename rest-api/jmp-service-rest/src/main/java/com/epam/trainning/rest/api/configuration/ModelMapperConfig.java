package com.epam.trainning.rest.api.configuration;

import com.epam.trainning.rest.api.dto.SubscriptionResponseDto;
import com.epam.trainning.rest.api.dto.UserResponseDto;
import com.epam.trainning.rest.api.model.Subscription;
import com.epam.trainning.rest.api.model.User;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.module.jsr310.Jsr310Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@AllArgsConstructor
public class ModelMapperConfig {
    @Bean
    public ModelMapper getModelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.registerModule(new Jsr310Module());
        Converter<String, LocalDate> strToLocalDate = ctx -> ctx.getSource() == null ? null : LocalDate.parse(ctx.getSource());
        Converter<LocalDate, String> localDateToStr = ctx -> ctx.getSource() == null ? null : ctx.getSource().toString();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.createTypeMap(Subscription.class, SubscriptionResponseDto.class)
                .addMappings(mapper -> {
                    mapper.map(subscription -> subscription.getUser().getId(), SubscriptionResponseDto::setUserId);
                    mapper.using(localDateToStr).map(Subscription::getStartDate, SubscriptionResponseDto::setStartDate);
                });
        modelMapper.createTypeMap(SubscriptionResponseDto.class, Subscription.class)
                .addMappings(mapper -> mapper.using(strToLocalDate).map(SubscriptionResponseDto::getStartDate, Subscription::setStartDate));
        modelMapper.createTypeMap(User.class, UserResponseDto.class)
                .addMappings(mapper -> mapper.using(localDateToStr).map(User::getBirthday, UserResponseDto::setBirthday));
        modelMapper.createTypeMap(UserResponseDto.class, User.class)
                .addMappings(mapper -> mapper.using(strToLocalDate).map(UserResponseDto::getBirthday, User::setBirthday));
        return modelMapper;
    }
}