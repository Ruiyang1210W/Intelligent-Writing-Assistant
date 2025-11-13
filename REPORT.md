# Project Report

## Challenges Faced

### Challenge 1: API Limiting
**Problem:** Open AI API required you to pay\
**Solution:** Paid 5$
**Learning:** Money can solve problems

### Challenge 2: Error Handling
**Problem:** JUnit run configure errors, the configure always run in Maven instead of Main file\
**Solution:** Uncheck the 'Delegate IDE build run to Maven'

## Testing Strategy
The testing strategy consisted of a total of 31 tests distributed across 4 test classes. These tests were categorized into different sections based on the functionality they were testing.
- The Design Pattern Tests comprised of 16 tests that verified the implementation of various design patterns such as Strategy, Factory, Observer, and Singleton within the software.
- The Model Layer Tests included 7 tests that focused on testing the state and behavior of the ContentModel within the application.
- Validation Tests were conducted to ensure the accurate validation of inputs, API keys, and filenames within the system. This category consisted of 11 tests that thoroughly examined the validation mechanisms implemented in the software.
- Singleton Tests comprised of 3 tests that assessed the proper management of the OpenAIClient instance within the application.

## Design Pattern Justifications
**Strategy Pattern:** Needed different AI behaviors (creative, professional, academic)\
**Factory Pattern:** Creating different request types cleanly\
**Observer Pattern:** Used to synchronize UI with Model in MVC architecture. InputPanel, OutputPanel, and ControlPanel are Observers.

## AI/LLM Usage Disclosure
Did you use AI tools (ChatGPT, Copilot, etc.)?\
Ruiyang used Claude to debug JUnit configure error
- Asked: Why I can't run directly in file and keep getting maven exec:exec error
- Modeified: Changed their suggestion to fit my error handler
Verified: Run successfully with Main.

## Time Spent: 
~ 30 hours (across 2 weeks)

## What We Learned
- OOP concept reinforced
- API integration insights
- Design pattern understanding

## If We Had More Time
Answered by our Intelligent-Writing-Assistant:
- In order to enhance the functionality of the Intelligent Writing Assistant project, there are several features and improvements that we would add if given more time. These include implementing voice output and incorporating multi-language translation capabilities. These additions would further enhance the usability and accessibility of the tool. For further details, please refer to the report.md.