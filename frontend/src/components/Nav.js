import logo from '../images/white.png'
import colorLogo from '../images/orange.png'

const Nav = ({minimal,authToken,setShowModal,showModal, setIsSignUp}) => {

    const handleClick = () => {
        setShowModal(true)
        setIsSignUp(false)
    }


    return (
        <nav>
            <div className="logo-container">
                <img className="logo" src={minimal ? colorLogo : logo}/>
            </div>

            {!authToken && !minimal && <button
             className="nav-button"
             onClick={handleClick}
             disabled={showModal}
             >Log in</button>}

        </nav>
    )
}
export default Nav