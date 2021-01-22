using System.Collections;
using System;
using UnityEngine;
using UnityEngine.Advertisements;


namespace MazesAndMore
{
    public class AdManager : MonoBehaviour, IUnityAdsListener
    {
#if UNITY_ANDROID
        private static readonly string storeID = "3979827";
#elif UNITY_IOS
    private static readonly string storeID = "3979826";
#endif
        private static readonly string videoID = "video";
        private static readonly string rewardedID = "rewardedVideo";
        private static readonly string bannerID = "Banner";

        private Action adSuccess;
        private Action adSkipped;
        private Action adFailed;

        private static bool noMoreAds = false;

#if UNITY_EDITOR
        private static bool testMode = true;
#else
    private static bool testMode = false;
#endif

        public static AdManager instance;

        void Awake()
        {
            if (instance != null)
            {
                DestroyImmediate(gameObject);
            }
            else
            {
                instance = this;
                DontDestroyOnLoad(gameObject);
                Advertisement.AddListener(this);
                Advertisement.Initialize(storeID, testMode);
            }
        }
        public static void ShowStandardAd()
        {
            if (Advertisement.IsReady(videoID) && !noMoreAds)
            {
                Advertisement.Show(videoID);
            }
        }

        public static void ShowBanner()
        {
            if (!noMoreAds)
                instance.StartCoroutine(ShowBannerWhenReady());
            else HideBanner();
        }
        public static void HideBanner()
        {
            Advertisement.Banner.Hide();
        }

        private static IEnumerator ShowBannerWhenReady()
        {
            while (!Advertisement.IsReady(bannerID))
                yield return new WaitForSeconds(0.5f);
            if (!noMoreAds)
            {
                Advertisement.Banner.SetPosition(BannerPosition.BOTTOM_CENTER);
                Advertisement.Banner.Show(bannerID);
            }
            else
                HideBanner();

        }

        public static void ShowRewardedAd(Action success, Action skipped, Action failed)
        {
            instance.adSuccess = success;
            instance.adSkipped = skipped;
            instance.adFailed = failed;
            if (Advertisement.IsReady(rewardedID))
                Advertisement.Show(rewardedID);
           
        }

        public void OnUnityAdsDidFinish(string placementId, ShowResult showResult)
        {
            if (placementId == rewardedID)
            {
                switch (showResult)
                {
                    case ShowResult.Finished:
                        adSuccess();
                        break;
                    case ShowResult.Skipped:
                        adSkipped();
                        break;
                    case ShowResult.Failed:
                        adFailed();
                        break;
                }
            }
        }

        public static void DeactivateAds()
        {
            noMoreAds = !noMoreAds;
        }
        public void OnUnityAdsDidError(string message) { }
        public void OnUnityAdsDidStart(string placementId) { }

        public void OnUnityAdsReady(string placementId) { }
    }
}
