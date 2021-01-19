using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore {
    public class Ad : MonoBehaviour
    {
        public void RewardedAdHints()
        {
            AdManager.ShowRewardedAd(SuccessHint, Skip, Failed);
        }
        public void StandardAd()
        {
            AdManager.ShowStandardAd();
        }
        public void ShowBanner()
        {
            AdManager.ShowBanner();
        }
        public void HideBanner()
        {
            AdManager.HideBanner();

        }

        public void NoMoreAds()
        {
            AdManager.DeactivateAds();
            GameManager.instance.NoMoreAds();

        }

        void SuccessHint()
        {
            GameManager.instance.amountOfHints += 1;
        }

        void Skip()
        {
            Debug.Log("Ad skipped");
        }

        void Failed()
        {
            Debug.Log("Ad failed to load");
        }
    }
}
