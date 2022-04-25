const Chat = ({recipes})=> {
    return (
        <>
        <div className="chat-display">
            {recipes && recipes.map((description,_index)=>(
                <div>
                    <p className="recipe-title">{description.name}</p>
                    <p>{description.description}</p>
                    </div>
            ))}
            
        </div>
        </>
    )
}

export default Chat