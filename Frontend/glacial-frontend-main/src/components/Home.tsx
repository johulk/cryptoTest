import "../styles/Home.css";
import { createPost } from "../services/HomeService";
import { useState } from "react";
import { FiHeart, FiRepeat, FiMessageCircle } from "react-icons/fi";
import { IoIosSend } from "react-icons/io";
import { FaTrophy } from "react-icons/fa";
import avatar from "../images/ronaldo.png";
import trump from "../images/trump.png";
import obama from "../images/obama.png";
import elon from "../images/elon.png";
import antonio from "../images/antonio.png";
import marcelo from "../images/marcelo.png";

export default function Home() {
    const [postContent, setPostContent] = useState("");
    const [likedPosts, setLikedPosts] = useState({});
    const [repostedPosts, setRepostedPosts] = useState({});
    const [commentedPosts, setCommentedPosts] = useState({});

    const [posts, setPosts] = useState([
        {
            id: 1,
            content: "This is the first post! I'm excited to share my thoughts with all of you today. It's been a great start to the week, and I'm looking forward to what lies ahead. The world is changing fast, and I have some big ideas that I want to share with everyone. Stay tuned for more updates!",
            username: "Donald Trump",
            avatar: trump,
            date: new Date(),
            likes: 10,
            reposts: 5,
            comments: 3
        },
        {
            id: 2,
            content: "Another interesting post. As a former president, I've seen the world from a unique perspective, and there's a lot that we need to discuss. The current political climate is intense, but I believe in the power of the people to make a change. Let's keep the conversation going and work towards a better future for everyone.",
            username: "Barack Obama",
            avatar: obama,
            date: new Date(),
            likes: 20,
            reposts: 7,
            comments: 5
        },
        {
            id: 3,
            content: "Here comes another one! Innovation is key to driving progress in today's world. As we continue to advance in technology, new challenges arise. But I'm confident that the solutions are out there. We just need to keep pushing the boundaries of what's possible, thinking outside the box and creating a future where everyone can thrive. Excited for what's coming next!",
            username: "Elon Musk",
            avatar: elon,
            date: new Date(),
            likes: 15,
            reposts: 8,
            comments: 2
        },
        {
            id: 4,
            content: "Portugal is at a crucial moment in history. As we move forward, we must ensure economic stability while maintaining social equity. The decisions we make today will shape the country for generations to come. Let's build a stronger Portugal together.",
            username: "António Costa",
            avatar: antonio,
            date: new Date(),
            likes: 8,
            reposts: 3,
            comments: 1
        },
        {
            id: 5,
            content: "The role of a president is to listen, to understand, and to serve the people. In these challenging times, it's more important than ever to stay united and work towards a better future. Together, we can overcome any obstacle.",
            username: "Marcelo Rebelo de Sousa",
            avatar: marcelo,
            date: new Date(),
            likes: 12,
            reposts: 6,
            comments: 4
        }
    ]);

    const generateRandomCA = () => {
        const chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        let ca = "";
        for (let i = 0; i < 38; i++) {
            ca += chars.charAt(Math.floor(Math.random() * chars.length));
        }
        return ca;
    };

    const leaderboard = [
        { username: "Donald Trump", avatar: trump, ca: generateRandomCA(), x: 1.2 },
        { username: "Barack Obama", avatar: obama, ca: generateRandomCA(), x: 2.0 },
        { username: "Elon Musk", avatar: elon, ca: generateRandomCA(), x: 3.5 },
        { username: "António Costa", avatar: antonio, ca: generateRandomCA(), x: 2.8 },
        { username: "Marcelo Rebelo de Sousa", avatar: marcelo, ca: generateRandomCA(), x: 4.0 }
    ];

    const sortedLeaderboard = leaderboard.sort((a, b) => b.x - a.x);

    const handlePostSubmit = async (e) => {
        e.preventDefault();
    
        if (!postContent.trim()) return; // Avoid sending empty posts
    
        try {
            await createPost({ message: postContent });
            setPostContent(""); // Clear after successful post
            console.log("✅ Post created successfully.");
        } catch (error) {
            console.error("❌ Failed to create post:", error);
        }
    };

    const formatDate = (date) => {
        return date.toLocaleString();
    };

    const toggleState = (state, setState, postId, key) => {
        setState(prev => ({ ...prev, [postId]: !prev[postId] }));
        setPosts(prevPosts =>
            prevPosts.map(post =>
                post.id === postId
                    ? { ...post, [key]: post[key] + (state[postId] ? -1 : 1) }
                    : post
            )
        );
    };

    return (
        <div id="home">
            <div className="home-left">
                <form onSubmit={handlePostSubmit} className="create-post">
                    <img src={avatar} alt="Logo" className="user-logo" />
                    <textarea
                        value={postContent}
                        onChange={(e) => setPostContent(e.target.value)}
                        placeholder="What's on your mind?"
                        rows="1"
                        required
                    ></textarea>
                    <button type="submit">
                        <IoIosSend className="icon"/> Post
                    </button>
                </form>
                <div className="posts">
                    {posts.map((post) => (
                        <div key={post.id} className="post">
                            <div className="post-header">
                                <img src={post.avatar} alt={post.username} className="user-logo" />
                                <div className="post-info">
                                    <span className="post-username">{post.username}</span>
                                    <span className="post-date">{formatDate(post.date)}</span>
                                </div>
                            </div>
                            <p className="post-message">{post.content}</p>
                            <div className="post-stats">
                                <div className="stat-item">
                                    <button className={`posts-button ${likedPosts[post.id] ? "liked" : ""}`}
                                            onClick={() => toggleState(likedPosts, setLikedPosts, post.id, "likes")}>
                                        <FiHeart /> {post.likes}
                                    </button>
                                </div>
                                <div className="stat-item">
                                    <button className={`posts-button ${repostedPosts[post.id] ? "reposted" : ""}`}
                                            onClick={() => toggleState(repostedPosts, setRepostedPosts, post.id, "reposts")}>
                                        <FiRepeat /> {post.reposts}
                                    </button>
                                </div>
                                <div className="stat-item">
                                    <button className={`posts-button ${commentedPosts[post.id] ? "commented" : ""}`}
                                            onClick={() => toggleState(commentedPosts, setCommentedPosts, post.id, "comments")}>
                                        <FiMessageCircle /> {post.comments}
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
            <div className="home-right">
                <div className="leaderboard">
                    <h2>Leaderboard</h2>
                    <ul>
                        {sortedLeaderboard.slice(0, 5).map((user, index) => (
                            <li key={index} className={`leaderboard-item position-${index + 1}`}>
                                <div className="leaderboard-trophy"><FaTrophy /></div>
                                <img src={user.avatar} alt={user.username} className="leaderboard-avatar" />
                                <div className="leaderboard-info">
                                    <span className="leaderboard-username">{user.username}</span>
                                    <span className="leaderboard-ca">CA: {user.ca}</span>
                                </div>
                                <span className="multiplier">x{user.x.toFixed(1)}</span>
                            </li>
                        ))}
                    </ul>
                </div>
                <div className="notifications">
                </div>
            </div>
        </div>
    );
}