import { useCookies } from 'react-cookie'
import {useNavigate} from 'react-router-dom'
const ChatHeader = ({ user }) => {
    let navigate= useNavigate()
    const[cookies,setCookie,removeCookie] = useCookies(['user'])
    const logout = () => {
        removeCookie("AuthToken",cookies.AuthToken)
        console.log("logging out")
        navigate('/')
        window.location.reload()
    }
    
    return (
        <div className="chat-container-header">
            <div className="profile">
                <h3>Welcome {user.username}</h3>

            </div>
            <i className="log-out-icon" onClick={logout}> Sign Out 🡨</i>
        </div>
    )
}

export  default ChatHeader