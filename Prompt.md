# Prompt used for AI tools
## AI tools used
1) ChatGPT GPT-6
2) Claude Sonnet 5.5 Medium
3) Claude code Opus 5.5 Medium

## Prompts
### ChatGPT
1) Let's first set up playwright in java, in vs code. what I have rn is in typescript so should i make a new project and somehow inject pw there? Guide me
2) I created those folders, what's next?
3) Why is it all saying that they are non project files how do I fix it
4) Explain what specs, fixtures and data factory are
5) In pw docs It's recommended to create a new BrowserContext for each test to ensure they don't interfere with each other.  Where do we implement that? in fixture?
6) Will it execute in parralel?
7) How do I do the UserFactory?


### ChatGPT usage note
As far as I'd like to be transparent about AI usage, most of the prompts are written in informal language, hence some are altered preserving the context of the prompts. I have used ChatGPT in setting up the test environment, particularly with TestSetup fixture and base for UserFactory for RegisterTest spec besides open source tutorials on the internet for Java/JUnit for a headstart. 2nd, 4th and 5th tasks were written fully by me without AI's help, while 3rd task was particularly painful since my version of the test kept failing. The conversation with ChatGPT easily racked up in volume due to countless debugging with 22 tries on one test that suggest about 20 fixes. Truth to say, ChatGpt was not helping after hours of trying to fix it, so I switched to Claude, which helped me resolve the problem in 4 prompts. The devastation...  
Other than that I have used ChatGPT for confirmation questions, e.g. 'does navigate refresh the page>', 'Is there a better way to cinfirm that the alert was bad or good than checking if the alert contains some text? like I see the alerts have class alert alert-danger mt-3 and alert alert-success mt-3' or one line answer questions like 'how do I convert from string to integer?', 'how do I get text from the item?' and etc.

### Claude
1) I am testing a site https://practicesoftwaretesting.com/ with playwright java junit, the objective is to accomplish these 5 tests:  
Register a new customer  
Log in  
Buy a product  
Compare two products  
Reset a password  
I am stuck on the third as it's not passing the assertion in checkout, I spent too much time trying to debug it to no avail.  
This is the test output:  
[...]  
And here are the classes:
[BuyProductTest]
[HomePage]
[ProductPage]
[CheckoutPage]
2) It is pointing at something in cheout now, is it failing to autocomplete or asserting before autocomplete can finish? How can I fix it?  
[error log]
3) I changed the state into fill now still something
[error log]
4) [sout message]
how come first fields are empty? I was just reusing the ones from login and register(create) [UserFactory]
### Claude code
1) Can we implement logging in once so that later tests start signed in in this project?