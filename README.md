# What is MVI architecture?

MVI stands for Model-View-Intent. This pattern has been introduced recently in Android. It works based on the principle of unidirectional and cylindrical flow inspired by the Cycle.js framework.

Let’s see what is the role of each component of MVI.

*  **Model**: Unlike other patterns, In MVI Model represents the state of the UI.
    i.e for example UI might have different states like Data Loading, 
    Loaded, Change in UI with user Actions, Errors, User current screen position states. 
    Each state is stored as similar to the object in the model.
    
* **View**: The View in the MVI is our Interfaces which can be implemented in Activities and fragments. 
It means to have a container which can accept the different model states and display it as a UI. 
They use observable intents(Note: This doesn't represent the Android traditional Intents) to respond to user actions.
    
* **Intent**: Even though this is not an Intent as termed by Android from before.
The result of the user actions is passed as an input value to Intents. 
In turn, we can say we will be sending models as inputs to the Intents which can load it through Views.

## How does the MVI work?

User does an action which will be an Intent → Intent is a state which is an input to model → Model stores state and send the requested state to the View → View Loads the state from Model → Displays to the user. If we observe, the data will always flow from the user and end with the user through intent. It cannot be the other way, Hence its called Unidirectional architecture. If the user does one more action the same cycle is repeated, hence it is Cyclic.


![shape](https://s3.ap-south-1.amazonaws.com/mindorks-server-uploads/mvi_cyclic-49d9f8c2d3fe26b7.png)

