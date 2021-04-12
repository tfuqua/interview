# QR Code Generator: Interview Project 
Vincent Martin: vince@finalatomicbuster.net

# Description
QR Code Generator is a React application that gives you the ability to create a QR Code image based on an input string.  This input string may be just a normal string such as "I like dogs." or even a URL such as http://www.duckduckgo.com.

QR Codes can be created one after the other and are stored in a list located below the main form with the following criteria.
1. Duplicate QR Codes can not be added to the list.
2. New QR Codes created will appear on the top of the list.
3. QR Codes added to the list may be deleted.
4. QR Codes with a URL as content will be clickable once rendered in the list.

# Goals Accomplished
#### 1. Fetch Data from the backend Crud API you created
Data was fetched from the QR Code Generator API which is documented at http://goqr.me/api/doc/create-qr-code/.  This was done via the fetch() API as documented at https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API.

The query prameters that were used in this GET call are the following.
```
  data: A string that we wish to convert into a QR Code.
  size: A value that defines the size of the image returned.  Defines dimensions of n x n.
```

Once the button is hit to generate a QR Code a function is called with input data as stored in our `inputField` state that returns a JS Object with the following shape.
```
{
  input: String: inputString meant to be converted into QR code,
  image: String: representing ObjectURL referencing downloaded QR Code image,
}
```

On success this object is added to an array that is stored in our `qrCodes` state.
On error we then set a message into our `inputError` state.

#### 2. Display data from API onto your page (Table, List, etc.)
The API that was used is quite simple and only returns one image per request.  I assume that it would be good to show understanding of basic concepts like using map to iterate through data sets.  To demonstrate this I implemented the ability to store each of our requests in an array located in our state.  Once an item is added to the array it will display a simple component that renders the requested QR Code and the value used to create it.

The image itself is a blob referenced via an object URL as created by `URL.createObjectURL()` as documented at https://developer.mozilla.org/en-US/docs/Web/API/URL/createObjectURL.

Additionally we are able to delete any QR Code that is added to our list.  Doing this removes the item from the array stored in our state and also, importantly, revokes the ObjectURL that was created.  If we do not do this we can get into a scenario where memory leaks cause us problems down the road.

#### 3. Apply a styling solution of your choice to make your page look different (CSS, SASS, CSS-in-JS)
This was my first time workin with material-ui in quite some time.  My styling was quite minimal and was done via `makestyles()` and `useStyles()` functions provided by the material-ui project for styling.  Styling was limited to spacing and positioning of components and changing of button sizes.

#### 4. Have fun
#### Thoughts
This was a fun project.  It was quite hard to figure out what API to use.  I went ahead with something that could be conceived as being somewhat useful.  It was nice to work a little bit with the material-ui react components library.  I have used this in the past but recently have lived day to day in react-bootstrap.  

Working with the API was quite easy and I would suggest this API for anyone else who needs to make some QR codes.

Testing for this was done by creating a QR Code and verifying that it worked via my iPhone.  Additionally I attempted to come up with scenarios which would cause the API to fail such as altering the URL used in our network utility.  Error handling is quite simple and alerts users when a duplicate item is created or when the API cannot be reached.  This is communicated via the input text error feature of material-ui.

#### Future Work / Features
If I kept working on this I would spend more time styling the components.  I think that it could be made to look a lot nicer.  

I would also consider changing from showing every QR Code as a Card and instead use a Table that links to a Card that could popup when clicked.  

Additionally I would want to add the ability to share the QR Code more easily.  This could perhaps be done by letting you send it via an Email that would include the input string and the QR Code itself.

When we render our QRCode component we check to see if we are rendering a URL.  This check is overly simplistic and is not something I would include in a production application.  A better solution would have to be found if given more time.

Finally, if given more time and this was a production application I would certainly work some automated testing into it.  At the very least to test the input fields
