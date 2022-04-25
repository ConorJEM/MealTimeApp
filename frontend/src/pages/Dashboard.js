import TinderCard from 'react-tinder-card'
import {useEffect, useState} from 'react'
import { useCookies } from 'react-cookie'
import ChatContainer from '../components/ChatContainer'  
import axios from 'axios'

const Dashboard = () => {
  const[user,setUser]  = useState(null)
  const[meals,setMeals]  = useState(null)
  const[cookies,setCookie,removeCookie] = useCookies(['user'])

  const authToken = cookies.AuthToken
  
   const getUser = async () => {
     await fetch('https://mealtimebackend.azurewebsites.net/hunter/profile',{
        
       method: 'GET',
       headers:{
         'Authorization':'Bearer ' + authToken
       }
     }).then(res => res.json()) // if response is json, for text use res.text()
     .then(data => setUser(data))
     .then(console.log(user)) // if text, no need for JSON.stringify
     .catch(error => console.error('Error:', error));
     
   }

   const getMeals = async () => {
    await fetch('https://mealtimebackend.azurewebsites.net/hunter/meals',{
        
        method: 'GET',
        headers:{
         'Authorization':'Bearer ' + authToken
       }
     }).then(res => res.json())
        .then(data => setMeals(data))
        .catch(error => console.error('Error:',error))

   }



   
   useEffect(() =>{
     getUser()
     getMeals()
     
   },[])
   
   console.log('user',user)
   console.log('meals',meals)

   const updateFavourites = async (swipedMealId) => {
    await fetch(`https://mealtimebackend.azurewebsites.net/hunter/addToFavourites/${swipedMealId}`,{
      
      method: 'POST',
      headers:{
        'Authorization':'Bearer ' + authToken
      }
    }).then(res => res.json())
      
       .catch(error => console.error('Error:',error))
   }

    const [lastDirection, setLastDirection] = useState()
  
    const swiped = (direction, swipedMealId) => {

      if(direction === 'right'){
        updateFavourites(swipedMealId)
      }

      setLastDirection(direction)
    }
  
    const outOfFrame = (name) => {
      console.log(name + ' left the screen!')
    }



    return (
      <>
      {user &&
        <div className="dashboard">
            <ChatContainer user={user}/>
            <div className="swipe-container">
                <div className="card-container">
                        {meals?.map((character) => 
                            <TinderCard
                            className='swipe'
                            key={character.name} 
                            onSwipe={(dir) => swiped(dir, character.id)} 
                            onCardLeftScreen={() => outOfFrame(character.name)}>
                            <div style={{ backgroundImage: 'url(' + character.imagePath + ')' }}  className='card'>
                                    <h3>{character.name}</h3>
                            </div>
                            </TinderCard>
                        )}
                        <div className="swipe-info">
                            {lastDirection ? <p>You swiped {lastDirection}</p>: <p/>}
                        </div>


                </div>
            </div>

        </div>}
        </>
    )
}
export default Dashboard