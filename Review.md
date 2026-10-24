# Review of the AI responses
### Work with ChatGPT
I have used ChatGPT a lot to understand how Playwright works, and what kind of methods I could use for particular actions. It helped me construct the structure of the project, as well as set up TestSetup, and UserFactory/User, which I modified to fit my tasks on the practice software testing site.
Unfortunately not all of the responses were helpful, particularly for Task 3, however it did a good job on explaining concepts, suggesting better alternatives to the methods I have been using. It provided me with more narrow understanding on working with Playwright with Java since Playwright's documentation wasn't as clear to me.

### Work with Claude
I used Claude only for Task 3 to help me fix the test failing. That's how I discovered that the seeded account had prefilled fields which were loading and wiping off my actions on the page, gradually fixing it by asserting prefilled fields were loaded before taking actions. Was pretty helpful considering I fought ChatGPT about task 3 for 3 hours straight.

### Working with Claude Code
I used Claude Code to implement authorised state for tests that required it. It did edit my classes a bit to fit the new auth context. I could further use the authenticated state, but besides buying the product I didn't particularly need it anywhere.