# Intelligent Writing Assistant

## Team Members
- Ruiyang Wang (016087383)
- Colin Shiung (017426461)

## Project Description
A writing tool integrates the OpenAI API, focusing on areas like Job Cover letters, Statements of Purposes, and essays, making them more human-sounding.

## Features Implemented
- [x] Creative mode, Professional mode, Academic mode
- [x] MVC Architecture
- [x] API integration
- [x] Junit Tests 28 passed
- [ ] Swing GUI (Working)

## Design Patterns Used
1. **Strategy Pattern** - Different writing modes
2. **Factory Pattern** - Request creation
3. **Observer Pattern** - UI 

## Setup Instructions
1. Get API key from https://platform.openai.com/
2. `export API_KEY=your_key`
3. Run Main.java

### Prerequisites
- Java 11 or higher
- OpenAI API key

### Installation
1. Clone repository
2. Copy `config.properties.example` to `config.properties`
3. Add your OpenAI API key to `config.properties`
4. Run `Main.java`

### Dependencies
- org.json (version)
- JUnit 5.10.1


## API Usage & Costs
- Model used: gpt-3.5-turbo
- Estimated cost per request: $0.002
- Cost management strategies


## Demo link: 

## Future Enhancements
- Feature we'd add with more time
