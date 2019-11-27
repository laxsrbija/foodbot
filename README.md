![](./resources/images/logo.png)

FoodBot is a system for communication between food providers (e.g. catering services or restaurants) and corporate users.
It manages the provider's weekly menu and notifies the end-users via a Skype group.  

## Features
* Menu storage and management
* Automatic parsing of the newly received menu
* User review system
* Skype messaging via Skype Web Client
* Daily reminder at a configurable predefined time
* Placeholder and configuration system
* Random daily greeting message

## Overview
FoodBot stores a weekly menu given by the food provider. 
Based on the current day of the week and the configured time, it will send a reminder message to the predefined Skype group chat.

The actual message that is being sent can be fine-tuned using both system and user-defined placeholders. 
Before sending the message, FoodBot will replace the provided placeholders with actual data.

The food provider can notify the system of menu changes by email. 
FoodBot will try to parse the provided menu but will leave a copy of the original message.
A notification is then sent to each person marked as a reviewer.
Reviewers can modify the parsed values if needed and then publish the new menu.

## Getting started

## Starting a development version
