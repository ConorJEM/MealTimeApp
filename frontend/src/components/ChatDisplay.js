import { useEffect,useState } from "react"
import { useCookies } from 'react-cookie'
import Chat from "./Chat"


const ChatDisplay = ({clickedMeal}) => {

    const[recipes,setRecipes]  = useState(null)
    const[cookies,setCookie,removeCookie] = useCookies(['user'])
    const authToken = cookies.AuthToken
    const clickedMealId = clickedMeal?.id


    const getRecipes = async () => {
        await fetch(`https://mealtimebackend.azurewebsites.net/hunter/getRecipesForMeal/${clickedMealId}`,{
           method: 'POST',
           headers:{
             'Authorization':'Bearer ' + authToken
           }
         }).then(res => res.json())
            .then(data => setRecipes(data))
            .then(console.log('recipes',recipes))
            .catch(error => console.error('Error:',error))
       }

    useEffect(()=>{
        console.log('clicked meal here',clickedMeal)
        getRecipes()
    },[])
    console.log('recipes',recipes)



    return (
        <>
            <div>
                <Chat recipes={recipes}/>
            </div>
        </>


    )


}

export default ChatDisplay