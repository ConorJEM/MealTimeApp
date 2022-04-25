import ChatHeader from "./ChatHeader"
import MatchesDisplay from "./MatchesDisplay"
import ChatDisplay from "./ChatDisplay"
import {useState} from 'react'
const ChatContainer = ({user}) => {

    const [clickedMeal,setClickedMeal] =useState(null)
    console.log('clickedMeal',clickedMeal)
    return (
        <div className="chat-container">

            <ChatHeader user={user}/>

            <div>
                <button className="option" onClick={()=> setClickedMeal(null)}>Favourites</button>
                <button className="option" disabled={!clickedMeal}>Recipes</button>
            </div>

            {!clickedMeal && <MatchesDisplay setClickedMeal={setClickedMeal}/>}

            {clickedMeal && <ChatDisplay clickedMeal={clickedMeal}/>}

        </div>
    )
}

export  default ChatContainer