
import {useState} from 'react'
import axios from 'axios'
import {useNavigate} from 'react-router-dom'
import {useCookies} from 'react-cookie'


const AuthModal = ({setShowModal,isSignUp}) => {

    const [username,setusername]=useState(null)
    const [password,setPassword]=useState(null)
    const [confirmPassword,setConfirmPassword]=useState(null)
    const [error,setError] = useState(null)
    const [cookies,setCookie,removeCookie] = useCookies(["user"])

    let navigate= useNavigate()

    console.log(username,password,confirmPassword)



    const handleClick = () =>{
        setShowModal(false)
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            if( isSignUp && (password !== confirmPassword)){
                setError('Passwords need to match!')
                return
            }

            const response = await axios.post(`https://mealtimebackend.azurewebsites.net/hunter/${isSignUp ? 'register' : 'login'}`, {username,password})

            setCookie('AuthToken',response.data.accessKey)

            const success = response.status === 200

            if (success) {
                navigate('/dashboard')
            }
            window.location.reload()
            
        } catch (error) {
            console.log(error)
        }

    }


    return (
        <div className="auth-modal">
            <i className="close-icon" onClick={handleClick} >✖</i>
            <h2>{isSignUp? 'CREATE ACCOUNT' : 'LOG IN'}</h2>
            <p>By clicking log in, you agree to our terms.</p>
            <form onSubmit={handleSubmit}>
                <input
                    type="username"
                    id="username"
                    name="username"
                    placeholder="username"
                    required={true}
                    onChange={(e)=> setusername(e.target.value)}
                />
                <input
                    type="password"
                    id="password"
                    name="password"
                    placeholder="password"
                    required={true}
                    onChange={(e)=> setPassword(e.target.value)}
                />
                {isSignUp&&<input
                    type="password"
                    id="password-check"
                    name="password-check"
                    placeholder="confirm password"
                    required={true}
                    onChange={(e)=> setConfirmPassword(e.target.value)}
                />}
                <input className="secondary-button" type="submit"/>

                <p>{error}</p>

            </form>
            <hr/>
            <h2> GET THE APP</h2>

    

        </div>
    )
}
export default AuthModal