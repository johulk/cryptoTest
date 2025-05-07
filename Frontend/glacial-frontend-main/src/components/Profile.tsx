import "../styles/Profile.css";
import avatar from "../images/ronaldo.png";
import banner from "../images/banner.png";

function Profile() {
    return (
        <div id="profile">
            <div className="information">
                <div className="content">
                    <img src={banner} alt={'Banner'} id={'banner'} />
                    <img src={avatar} alt={'Avatar'} id={'avatar'} />
                    <p className="username">@{'Daniel'}
                        <span>
                            {'formatRole(userProfile.role)'}
                        </span></p>
                    <p className="description">{'LCC'}</p>
                    <ul className="stats">
                        <li>Posts<span>{'userProfile?.postsCount'}</span></li>
                        <li>Followers<span>{'userProfile?.followersCount'}</span></li>
                        <li>Following<span>{'userProfile?.followingCount'}</span></li>
                    </ul>
                </div>
            </div>

            <div id="feed">
                <ul className="header">
                    <li className={"selected1"} ><img src={''} alt="Posts Icon"/>Posts</li>
                    <li className={"selected2"} ><img src={''} alt="Reposts Icon"/>Reposts</li>
                    <li className={"selected3"} ><img src={''} alt="Comments Icon"/>Comments</li>
                    <li className={"selected4"} ><img src={''} alt="Likes Icon"/>Likes</li>
                </ul>
            </div>

        </div>
    );
}

export default Profile;