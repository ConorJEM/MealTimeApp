import { useEffect,useState } from "react"
import axios from 'axios'
import { useCookies } from 'react-cookie'
const MatchesDisplay = ({setClickedMeal}) => {

    const[favourites,setFavourites]  = useState(null)
    const[cookies,setCookie,removeCookie] = useCookies(['user'])
    const authToken = cookies.AuthToken

    const getFavourites = async () => {
        await fetch('https://mealtimebackend.azurewebsites.net/hunter/viewFavourites',{
           method: 'GET',
           headers:{
             'Authorization':'Bearer ' + authToken
           }
         }).then(res => res.json())
            .then(data => setFavourites(data))
            .then(console.log('favourites',favourites))
            .catch(error => console.error('Error:',error))
       }
       const removeFavourite = async (match) => {
        await fetch(`https://mealtimebackend.azurewebsites.net/hunter/removeFromFavourites/${match.id}`,{
           method: 'POST',
           headers:{
             'Authorization':'Bearer ' + authToken
           }
         }).then(res => res.json())
            .catch(error => console.error('Error:',error))

            getFavourites()
            setClickedMeal(null)
       }


       useEffect(()=>{
           getFavourites()
       },[favourites])

       console.log('favourites',favourites)

    return (
        <div className="matches-display">
            {favourites?.map((match,_index)=>(
                <div key={{_index}} className="match-card" onClick={() => setClickedMeal(match)}>
                    <div className="favouritesBar">
                    <div className="img-container">
                        <img src={match?.imagePath} alt={match?.name}/>
                    </div>
                    <div className="delete-icon" onClick={() =>removeFavourite(match)} >
                        <i>Remove X</i>
                    </div>
                    </div>
                    <h3>{match?.name}</h3>
                </div>
            ))}
        </div>
    )
}

export  default MatchesDisplay